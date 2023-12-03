package cams.models;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import cams.models.camp.Camp;
import cams.models.camp.CampEvent;
import cams.models.camp.CampObserver;
import cams.models.user.Staff;
import cams.models.user.Student;
import cams.models.user.User;

public class CamsData implements CampObserver {
    private static CamsData camsData;
    private final Map<String, User> userTable;
    private final Map<String, Camp> campTable;

    private CamsData() {
        userTable = new HashMap<>();
        campTable = new HashMap<>();
        readFromXSSF("student_list.xlsx", "staff_list.xlsx");
    }

    public static CamsData getInstance() {
        if (camsData == null)
            camsData = new CamsData();
        return camsData;
    }

    public static void close() {
        camsData = null;
    }

    public User getUser(String id) {
        return userTable.get(id.toUpperCase());
    }

    public void addUser(User user) {
        userTable.put(user.getId().toUpperCase(), user);
    }

    public Collection<Camp> getAllCamps() {
        return campTable.values();
    }

    public Camp getCamp(String name) {
        return campTable.get(name.toUpperCase());
    }

    public void addCamp(Camp camp) {
        campTable.put(camp.getName().toUpperCase(), camp);
    }

    public void removeCamp(Camp camp) {
        campTable.remove(camp.getName().toUpperCase());
        camp.delete();
    }

    public void readFromXSSF(String studentPath, String staffPath) {
        try (FileInputStream fileIn = new FileInputStream(studentPath);
                Workbook wb = new XSSFWorkbook(fileIn)) {
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue;

                boolean isRowEmpty = true;
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                if (isRowEmpty)
                    break;

                List<String> values = new ArrayList<>();
                for (Cell cell : row)
                    values.add(cell.toString());

                addUser(new Student(
                        values.get(1).substring(0, values.get(1).indexOf('@')),
                        values.get(0), values.get(2)));
            }
        } catch (IOException ignored) {
        }

        try (FileInputStream fileIn = new FileInputStream(staffPath);
                Workbook wb = new XSSFWorkbook(fileIn)) {
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if (row.getRowNum() == 0)
                    continue;

                boolean isRowEmpty = true;
                for (Cell cell : row) {
                    if (cell != null && cell.getCellType() != CellType.BLANK) {
                        isRowEmpty = false;
                        break;
                    }
                }
                if (isRowEmpty)
                    break;

                List<String> values = new ArrayList<>();
                for (Cell cell : row)
                    values.add(cell.toString());

                addUser(new Staff(
                        values.get(1).substring(0, values.get(1).indexOf('@')),
                        values.get(0), values.get(2)));
            }
        } catch (IOException ignored) {
        }
    }

    @Override
    public void update(CampEvent event, Object payload) {
        if (event == CampEvent.NAME_CHANGE) {
            String oldName = (String) payload;
            Camp temp = campTable.get(oldName);

            campTable.remove(oldName);
            addCamp(temp);
        }
    }
}
