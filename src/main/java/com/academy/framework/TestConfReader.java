package com.academy.framework;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TestConfReader {
    private String sheetName;
    private List<String> url;
    private List<String> login;
    private List<String> password;
    private List<String> expectedErrMsg;

    public TestConfReader(String confParameterName, String sheetName) {
        List<String> url = new ArrayList<>();
        List<String> login = new ArrayList<>();
        List<String> password = new ArrayList<>();
        List<String> expectedErrMsg = new ArrayList<>();

        File file = new File( confParameterName );

        //  HSSFWorkbook workbook;
        XSSFWorkbook workbook;

        try {
            //workbook = new HSSFWorkbook( new FileInputStream( file ) );
            // HSSFSheet sheet = workbook.getSheet( sheetName );
            workbook = new XSSFWorkbook( new FileInputStream( file ) );
            XSSFSheet sheet = workbook.getSheet( sheetName );
            for (int i = 0; i <= sheet.getLastRowNum(); i++) {
                //  HSSFRow parRow = sheet.getRow( i );
                XSSFRow parRow = sheet.getRow( i );
                url.add( parRow.getCell( 0 ).getStringCellValue() );
                login.add( parRow.getCell( 1 ).getStringCellValue() );
                password.add( parRow.getCell( 2 ).getStringCellValue() );
                expectedErrMsg.add( parRow.getCell( 3 ).getStringCellValue() );
            }
        } catch (IOException e) {
            System.out.println( "Something wrong" );
            e.printStackTrace();
        }
        this.url = url;
        this.login = login;
        this.password = password;
        this.expectedErrMsg = expectedErrMsg;
        System.out.println( "read from excel conf file" );

    }

    @Override
    public String toString() {
        return "TestConfReader. sheetName: " + sheetName + ", url:" + url.toString() +
                ", login: " + login.toString() + ", password: " + password.toString() +
                ", expectedErrMsg=" + expectedErrMsg.toString();
    }

    public String getUrl(int i) {
        return url.get( i );
    }

    public int geListSize() {
        return login.size();
    }

    public String getLogin(int i) {
        return login.get(i);
    }

    public String getPassword(int i) {
        return password.get( i );
    }

    public String getExpectedErrMsg(int i) {
        return expectedErrMsg.get( i );
    }
}
