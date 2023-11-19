package cams.view.components.student;

import cams.camp.Camp;
import cams.camp.CampController;
import cams.domain.Student;
import cams.domain.StudentController;
import cams.repliable.Enquiry;
import cams.repliable.EnquiryEditor;
import cams.view.DisplayController;
import cams.view.base.ActionableItem;
import cams.view.base.Alert;
import cams.view.base.CommonElements;
import cams.view.base.ItemAction;
import cams.view.base.SelectionMenu;
import cams.view.components.repliable.EditEnquiryForm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class StudentEnquiryMenu extends SelectionMenu {
    public StudentEnquiryMenu(Scanner scanner, Enquiry enquiry) {
        super(scanner);
        DisplayController displayController = DisplayController.getInstance();
        StudentController studentController = StudentController.getInstance();
        Student currentUser = studentController.getCurrentStudent();
        CampController campController = CampController.getInstance();
        Camp camp = null;
        
        List<Camp> campsList = new ArrayList<Camp>(campController.getAllCamps());
        for(Camp currentCamp:campsList) {
            if(currentCamp.getEnquiries().contains(enquiry)) {
                camp = currentCamp;
            }
        }
        
        EnquiryEditor enquiryEditor = new EnquiryEditor(camp);

        setPrompt(CommonElements.getStatusBar("Enquiry Menu") + "\n" + "\"" + enquiry.getQuestion() + "\"" + "\n" + "Camp: " + camp.getCampInfo().getCampName() + "\n");

        addItem(new ActionableItem("Edit", new ItemAction() {
            public void execute() {
                if(enquiry.getReply().isBlank()) {
                    displayController.setNextDisplay(new EditEnquiryForm(scanner, enquiry));    
                }
                else {
                    displayController.setNextDisplay(new Alert("Enquiry cannot be edited", new StudentViewEnquiryMenu(scanner), scanner));
                }
                
            }
        }));

        addItem(new ActionableItem("Delete", new ItemAction() {
            public void execute() {
                if (enquiry.getReply().isBlank()) {
                    enquiryEditor.delete(enquiry);
                    currentUser.removeEnquiry(enquiry);
                    displayController.setNextDisplay(new Alert("Enquiry has been deleted", new StudentViewEnquiryMenu(scanner), scanner));
                } else {
                    displayController.setNextDisplay(new Alert("Enquiry cannot be deleted", new StudentViewEnquiryMenu(scanner), scanner));
                }
            }
        }));


        addItem(new ActionableItem("View Reply", new ItemAction() {
            public void execute() {
                if(enquiry.getReply().isBlank()){
                    displayController.setNextDisplay(new Alert("No reply yet", new StudentViewEnquiryMenu(scanner), scanner));
                }
                else{
                    displayController.setNextDisplay(new Alert(enquiry.getReply(), new StudentEnquiryMenu(scanner, enquiry), scanner));
                }
            }
        }));


        addItem(new ActionableItem("Back", new ItemAction() {
            public void execute() {
                displayController.setNextDisplay(new StudentViewEnquiryMenu(scanner));
            }
        }));
    }
}
