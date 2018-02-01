package control;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.FileChooser;
import model.Matrix;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import view.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class WindowController {

    private static File prodMatrixFile, readyUpFile;
    public static final int XLSX_FILE = 0;
    public static final int XLS_FILE = 1;
    private static int FILE_TYPE;

    private static Matrix answer;

    @FXML
    public void onFindButtonCLicked(ActionEvent event) {
        final FileChooser chooser = new FileChooser();
        chooser.setTitle("open excel file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xlsx File", "*.xlsx")
                , new FileChooser.ExtensionFilter("xls File", "*.xls")
        );
        final File file = chooser.showOpenDialog(Main.getPrimaryStage());
        prodMatrixFile = file;
        if (file != null) {
            if (chooser.getExtensionFilters().equals(chooser.getExtensionFilters().get(0)))
                FILE_TYPE = XLSX_FILE;
            else
                FILE_TYPE = XLS_FILE;
        }


    }


    @FXML
    public void onSaveClicked(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("open excel file");
        chooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("xlsx File", "*.xlsx")
                , new FileChooser.ExtensionFilter("xls File", "*.xls")
        );
        final File file = chooser.showSaveDialog(Main.getPrimaryStage());
        readyUpFile = file;
        if (file != null) {
            new Thread(new Runnable() {
                public void run() {
                    try {
                        ExcelController.workWithExcel(prodMatrixFile, readyUpFile, XLSX_FILE);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    createFile(file.getAbsolutePath());
                }
            }).start();
        }
    }

    private static void createFile(String initialFileName) {
        File temp = new File(initialFileName);
        try {
            temp.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
