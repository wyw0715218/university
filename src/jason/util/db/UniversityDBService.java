package jason.util.db;

import jason.model.University;
import org.apache.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

/**
 * Created by Jason on 2016/9/15.
 */
public class UniversityDBService {

    private static Logger logger = Logger.getLogger(UniversityDBService.class);

    public static void saveUniversity(List<University> universityList){

        GetDataSource getDataSource = null;
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            getDataSource = new GetDataSource();
            connection = getDataSource.getMysqlConnect();
            String sql = "INSERT INTO UNIVERSITY_INFO (name,mainUrl,region,type,is211,is985) VALUES (?,?,?,?,?,?);";

            ps = connection.prepareStatement(sql);
            for (University university : universityList){
                ps.setString(1,university.getName());
                ps.setString(2,university.getMailUrl());
                ps.setString(3,university.getRegion());
                ps.setString(4,university.getType());
                ps.setString(5,university.getIs211());
                ps.setString(6,university.getIs985());
                ps.addBatch();
            }
            ps.executeBatch();
        }catch (Exception e){
            logger.info("插入大学信息出错...");
        }finally {
            getDataSource.closeMysqlConnect(connection,ps,null);
        }
    }
}
