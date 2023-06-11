package com.example.fa22bcs040lab_final;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.*;

public class HelloApplication extends Application {

//Global
    File f=new File("src/data.txt");
     Male male[]=new Male[50];
     Female female[]=new Female[50];
     int fcount=0;
     int mcount=0;



    @Override
    public void start(Stage stage) throws IOException {

        for(int i=0;i<male.length;i++)
        {
            male[i]=new Male();
            female[i]=new Female();
        }

      //All Grid & Scenes
        GridPane g=new GridPane();
        Scene s=new Scene(g,500,500);
        g.setAlignment(Pos.CENTER);
        g.setHgap(10);
        g.setVgap(10);
        GridPane g1=new GridPane();
        Scene s1=new Scene(g1,500,500);
        g.setAlignment(Pos.CENTER);
        g1.setHgap(10);
        g1.setVgap(10);

        stage.setScene(s);
        Label top=new Label("BMI Calculator");
        Font f=new Font(20);
        top.setFont(f);
        g.add(top,4,2);
        g.add(new Label("Name"),3,3);
        g.add(new Label("Date of Birth"),3,4);
        TextField namet=new TextField();
        TextField date=new TextField();
        TextField month=new TextField();
        TextField year=new TextField();
        date.setPrefWidth(30);
        month.setPrefWidth(30);
        year.setPrefWidth(45);
        g.add(namet,4,3);
        HBox dob=new HBox(date,new Label(" _ "),month,new Label(" _ "),year);
        g.add(dob,4,4);
        ToggleGroup t=new ToggleGroup();
        RadioButton maler=new RadioButton("Male");
        RadioButton femalr=new RadioButton("Female");
        maler.setToggleGroup(t);
        femalr.setToggleGroup(t);
        Label hdisplay=new Label("0.0");

        HBox hbox=new HBox(maler,femalr);
        g.add(hbox,4,5);
        Slider heights=new Slider(100,250,10);
        heights.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                hdisplay.setText(String.valueOf(heights.getValue()));
            }
        });
        heights.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                hdisplay.setText(String.valueOf(heights.getValue()));
            }
        });
        g.add(heights,4,6);
        g.add(hdisplay,5,6);
        g.add(new Label("Height(cm) "),3,6);
        Slider weights=new Slider(20,200,10);
        Label wdisplay=new Label("0.0");
        weights.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                wdisplay.setText(String.valueOf(weights.getValue()));
            }
        });
        g.add(wdisplay,5,7);
        g.add(new Label("Weight(kg)"),3,7);
        g.add(weights,4,7);
        Button save=new Button("Save");
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(maler.isSelected())
                {
                    male[mcount].name=namet.getText();
                    male[mcount].birthdate= Integer.parseInt(date.getText());
                    male[mcount].birthmonth= Integer.parseInt(month.getText());
                    male[mcount].birthyear= Integer.parseInt(year.getText());
                    male[mcount].height=heights.getValue();
                    male[mcount].weight=weights.getValue();
                    male[mcount].gender="Male";
                    male[mcount].calculateBMI();
                    male[mcount].findage();
                    try {
                        ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("src/data.txt"));
                        Male m=male[mcount];
                        oos.writeObject(m);
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    mcount++;
                }
              if(femalr.isSelected())
              {
                  female[fcount].name=namet.getText();
                  female[fcount].birthdate= Integer.parseInt(date.getText());
                  female[fcount].birthmonth= Integer.parseInt(month.getText());
                  female[fcount].birthyear= Integer.parseInt(year.getText());
                  female[fcount].height= Double.parseDouble(String.valueOf(heights.getValue()));
                  female[fcount].weight=weights.getValue();
                  female[fcount].gender="Female";
                  female[fcount].calculateBMI();
                  female[fcount].findage();
                  try {
                      ObjectOutputStream oos=new ObjectOutputStream(new FileOutputStream("src/data.txt"));
                      Female m=female[fcount];
                      oos.writeObject(m);
                  } catch (Exception e) {
                      System.out.println(e);
                  }
                  fcount++;
              }
              namet.clear();
              date.clear();
              month.clear();
              year.clear();
              heights.setValue(100);
              weights.setValue(20);
              hdisplay.setText("0.0");
              wdisplay.setText("0.0");

            }
        });//save
        TableView t1=new TableView<>();
        Button display=new Button("Display List");
        display.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ObservableList<Person> all= FXCollections.observableArrayList();
                for(int i=0;i<mcount;i++)
                    all.add(male[i]);
                for(int i=0;i<fcount;i++)
                    all.add(female[i]);
                t1.setItems(all);
                stage.setScene(s1);
            }
        });

        TableColumn name=new TableColumn<>("Name");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn age=new TableColumn<>("Age");
        age.setCellValueFactory(new PropertyValueFactory<>("age"));
        TableColumn gender=new TableColumn<>("Gender");
        gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        TableColumn heighc=new TableColumn<>("Height");
        heighc.setCellValueFactory(new PropertyValueFactory<>("height"));
        TableColumn weighc=new TableColumn<>("Weight");
        weighc.setCellValueFactory(new PropertyValueFactory<>("weight"));
        TableColumn bmi=new TableColumn<>("BMI");
        bmi.setCellValueFactory(new PropertyValueFactory<>("BMI"));
        t1.getColumns().addAll(name,age,gender,heighc,weighc,bmi);
        g1.add(t1,3,4);
        Button back=new Button("Back");
        back.setOnAction(e->stage.setScene(s));
        g1.add(back,3,5);
        HBox hBox1=new HBox(save,display);
        g.add(hBox1,4,8);
        stage.setTitle("FA22-BCS-040");
        stage.show();
    } //start end
//Functions


    public static void main(String[] args) {
        launch();
    }//main end
}//class end
//Time to Fly