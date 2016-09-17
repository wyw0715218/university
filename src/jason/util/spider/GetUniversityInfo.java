package jason.util.spider;

import jason.model.University;
import jason.util.db.UniversityDBService;
import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Jason on 2016/9/15.
 */
public class GetUniversityInfo {

    private static Logger logger = Logger.getLogger(GetUniversityInfo.class);

    private static String hao123BaseUrl = "http://www.hao123.com/edu";

    public static String dealFirstPage(String url) {
        try {
            //hao123大学主页入口
            Document document = Jsoup.connect(url).get();
            //获取记载数据的table
            Element edu_container = document.getElementsByClass("edu-container").get(0);
            //选取到tbody
            Elements university_elements = edu_container.child(0).child(0).children();

            for (Element element : university_elements) {
                if (element.html().contains("first")) {
                    try {
                        Elements elements = element.children();
                        int size = elements.size();
                        //第一个element是地区
                        logger.info("处理地区:" + elements.get(0).text()+"的大学基本信息开始...");
                        //第二个element是普通本科院校
                        getRegionUniNumAndUrl(elements.get(0).text(),elements.get(1),1);
                        //第三个element是高职院校
                        getRegionUniNumAndUrl(elements.get(0).text(),elements.get(2),2);
                        //第四个element是独立学院
                        getRegionUniNumAndUrl(elements.get(0).text(),elements.get(3),3);
                        //第五个element是分校办学点
                        getRegionUniNumAndUrl(elements.get(0).text(),elements.get(4),4);
                        logger.info("处理地区:" + elements.get(0).text()+"的大学基本信息结束...");
                    }catch (Exception e){
                        logger.error("解析element内容出错==="+e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("获取url=" + url + "内容出错==="+e.getMessage());
        }
        return "";
    }

    private static int getUniNumber(String source){
        int num = 0;
        if (null == source){
            return num;
        }
        //提取数据
        Pattern number_reg = Pattern.compile("\\d+所");
        Matcher m = number_reg.matcher(source);
        if (m.find()){
            num = Integer.parseInt(m.group().replaceAll("所",""));
        }
        return num;
    }

    private static void getRegionUniNumAndUrl(String reigion,Element element,int i){
        int num = getUniNumber(element.text());
        String type = (i -1 )+"";
        if (num > 1){
            dealSecondPage(reigion,element.getElementsByTag("a").get(0).attr("href"),type);
        }
    }

    /**
     * 处理具体到地区的二级页面
     * @param region
     * @param url
     */
    private static void dealSecondPage(String region,String url,String type){
        try {
            Document document = Jsoup.connect(url).get();

            Elements elements = document.getElementsByClass("t1").get(0).child(0).child(0).child(1).child(0).child(0).child(0).children();

            University university;
            List<University> universityList = new ArrayList<>();

            for (Element element : elements){
                if (!element.html().contains("阳光高考")){
                    university = new University();
                    university.setName(element.child(0).child(0).child(0).text());
                    university.setType(type);
                    university.setRegion(region);
                    university.setMailUrl(element.child(0).getElementsByTag("a").get(0).attr("href"));
                    universityList.add(university);
                }
            }

            //保存大学基本信息到数据库
            UniversityDBService.saveUniversity(universityList);
        } catch (IOException e) {
            logger.error("无法连接区域="+region+"的url"+url);
        }

    }

    public static void main(String[] args) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.info("开始获取大学基本信息"+sdf.format(new Date())+"...");
        dealFirstPage(hao123BaseUrl);
        logger.info("获取大学基本信息"+sdf.format(new Date())+"结束...");
    }
}
