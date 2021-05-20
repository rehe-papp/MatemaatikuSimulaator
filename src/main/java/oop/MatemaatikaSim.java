package oop;

import javafx.application.Application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;


public class MatemaatikaSim extends Application {

    //loome piltide jaoks muutujad
    private Image pudel = new Image("pudel.png");
    private Image raamat = new Image("raamat.png");
    private Image uni = new Image("uni.png");
    private Image raha = new Image("raha.png");

    private Image tavaline = new Image("tavaline.png");
    private Image joob = new Image("alkohoolik.png");
    private Image õpib = new Image("arvutis.png");
    private Image magab = new Image("sleep.png");
    private Image töötab = new Image("hardhat.png");

    private Image backgroundDelta = new Image("delta.jpg");
    private Image backgroundKoduPäev = new Image("kodu_päev.jpg");
    private Image backgroundKoduÖö = new Image("kodu_öö.jpg");
    private Image backgroundFastFood = new Image("fastfood.jpg");
    private Image backgroundPirogov = new Image("pirogov.jpg");

    private int algusStat = 200;
    double canvasX = 450;
    double canvasY = 800;


    @Override
    public void start(Stage peaLava) throws Exception {

        Group juur = new Group(); // luuakse juur
        BorderPane piiriPaan = new BorderPane();


        //loome lõuendi ning muutujad, et pilte ekraanile kuvada
        Canvas lõuend = new Canvas(canvasX, canvasY);
        GraphicsContext gc = lõuend.getGraphicsContext2D(); // graafiline sisu
        gc.drawImage(backgroundKoduPäev, 0.0, canvasY-canvasX, canvasX, canvasX);
        gc.drawImage(tavaline, 0.0, canvasY-canvasX, canvasX, canvasX);
        juur.getChildren().add(lõuend);

        //seisundi riba
        HBox info = new HBox(30);
        info.setAlignment(Pos.BASELINE_CENTER);

        Text tekstRahulolu = new Text("Rahulolu: " + algusStat);
        tekstRahulolu.setFont(Font.font("null", FontWeight.BOLD, canvasX/30));

        Text tekstTarkus = new Text("Tarkus: " + algusStat);
        tekstTarkus.setFont(Font.font("null", FontWeight.BOLD, canvasX/30));

        Text tekstEnergia = new Text("Energia: " + algusStat);
        tekstEnergia.setFont(Font.font("null", FontWeight.BOLD, canvasX/30));

        Text tekstRaha = new Text("Raha: " + algusStat);
        tekstRaha.setFont(Font.font("null", FontWeight.BOLD, canvasX/30));

        info.getChildren().add(tekstRahulolu);
        info.getChildren().add(tekstTarkus);
        info.getChildren().add(tekstEnergia);
        info.getChildren().add(tekstRaha);


        //loome mängija isendi
        Muudab mängija = new Muudab(tekstRaha,tekstTarkus, tekstRahulolu,
                tekstEnergia,algusStat,algusStat,algusStat,algusStat);
        Thread uuendus = new Thread(mängija);
        uuendus.start();


        /**
         * loome vastavad nupud:
         *
         * 1) pudel, suurendab rahulolu, vähendab kõike muud, raha teistest veits kiiremini
         * 2) raamat, suurendab tarkust, vähendab kõike muud, rahulolu teistest veits kiiremini
         * 3) magamine, suurendab energiat, vähendab kõike muud, tarkust teistest veits kiiremini
         * 4) raha, suurendab raha, vähendab kõike muud, energiat teistest veits kiiremini
         *
         * nupule vajutusega uuendame tausta ning õpilast ning määrame vastava booleani true'ks
         * kõik teised booleanid muutuvad set meetodis false'iks
         */
        //pudel nupp
        Rectangle ristkulikPudel = new Rectangle(canvasX/18, canvasY/8, 100, 100);
        ristkulikPudel.setFill(new ImagePattern(pudel, 0, 0, 1, 1, true));

        ristkulikPudel.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me){
                System.out.println("sa jood");
                gc.setFill(Color.SNOW);
                gc.fillRect(0,0,canvasX, canvasY);
                gc.drawImage(backgroundPirogov, 0.0, canvasY-canvasX, canvasX, canvasX);
                gc.drawImage(joob, 0.0, canvasY-canvasX, canvasX, canvasX);
                mängija.setJoobBool(true);
            }
        });

        juur.getChildren().add(ristkulikPudel);  // ristkülik lisatakse juure alluvaks

        //raamat nupp
        Rectangle ristkulikRaamat = new Rectangle(canvasX/3.6, canvasY/8, 100, 100);
        ristkulikRaamat.setFill(new ImagePattern(raamat, 0, 0, 1, 1, true));

        ristkulikRaamat.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("sa opid");
                gc.setFill(Color.SNOW);
                gc.fillRect(0,0,canvasX, canvasY);
                gc.drawImage(backgroundDelta, 0.0, canvasY-canvasX, canvasX, canvasX);
                gc.drawImage(õpib, 0.0, canvasY-canvasX, canvasX, canvasX);
                mängija.setTarkusBool(true);
            }
        });

        juur.getChildren().add(ristkulikRaamat);  // ristkülik lisatakse juure alluvaks

        //uni nupp
        Rectangle ristkulikUni = new Rectangle(canvasX/2, canvasY/8, 100, 100);
        ristkulikUni.setFill(new ImagePattern(uni, 0, 0, 1, 1, true));

        ristkulikUni.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("sa magad");
                gc.setFill(Color.SNOW);
                gc.fillRect(0,0,canvasX, canvasY);
                gc.drawImage(backgroundKoduÖö, 0.0, canvasY-canvasX, canvasX, canvasX);
                gc.drawImage(magab, 0.0, canvasY-canvasX, canvasX, canvasX);
                mängija.setEnergiaBool(true);
            }
        });

        juur.getChildren().add(ristkulikUni);  // ristkülik lisatakse juure alluvaks

        //raha nupp
        Rectangle ristkulikRaha = new Rectangle(canvasX/1.4, canvasY/8, 100, 100);
        ristkulikRaha.setFill(new ImagePattern(raha, 0, 0, 1, 1, true));

        ristkulikRaha.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            public void handle(MouseEvent me) {
                System.out.println("sa tootad");
                gc.setFill(Color.SNOW);
                gc.fillRect(0,0,canvasX, canvasY);
                gc.drawImage(backgroundFastFood, 0.0, canvasY-canvasX, canvasX, canvasX);
                gc.drawImage(töötab, 0.0, canvasY-canvasX, canvasX, canvasX);
                mängija.setTööBool(true);
            }
        });

        juur.getChildren().add(ristkulikRaha);  // ristkülik lisatakse juure alluvaks


        piiriPaan.setTop(info);
        piiriPaan.setCenter(juur);

        Scene stseen1 = new Scene(piiriPaan, canvasX, canvasY, Color.SNOW);  // luuakse stseen


        /**
         * tegevuste muutmine, kuid klaviatuuri abil
         * Numbririda
         * 1 - joomine
         * 2 - õppimine
         * 3 - magamine
         * 4 - töötamine
         * 5 - chillime, n-ö tavaolek, kõik statid kahanevad aga aeglasemalt, kui muidu
         *
         * SPACE - paneb pausile ja võtab maha pausilt
         */

        stseen1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case DIGIT1:
                        System.out.println("sa jood");
                        gc.setFill(Color.SNOW);
                        gc.fillRect(0,0,canvasX, canvasY);
                        gc.drawImage(backgroundPirogov, 0, canvasY-canvasX, canvasX, canvasX);
                        gc.drawImage(joob, 0.0, canvasY-canvasX, canvasX, canvasX);
                        mängija.setJoobBool(true);
                        break;
                    case DIGIT2:
                        System.out.println("sa opid");
                        gc.setFill(Color.SNOW);
                        gc.fillRect(0,0,canvasX, canvasY);
                        gc.drawImage(backgroundDelta, 0.0, canvasY-canvasX, canvasX, canvasX);
                        gc.drawImage(õpib, 0.0, canvasY-canvasX, canvasX, canvasX);
                        mängija.setTarkusBool(true);
                        break;
                    case DIGIT3:
                        System.out.println("sa magad");
                        gc.setFill(Color.SNOW);
                        gc.fillRect(0,0,canvasX, canvasY);
                        gc.drawImage(backgroundKoduÖö, 0.0, canvasY-canvasX, canvasX, canvasX);
                        gc.drawImage(magab, 0.0, canvasY-canvasX, canvasX, canvasX);
                        mängija.setEnergiaBool(true);
                        break;
                    case DIGIT4:
                        System.out.println("sa tootad");
                        gc.setFill(Color.SNOW);
                        gc.fillRect(0,0,canvasX, canvasY);
                        gc.drawImage(backgroundFastFood, 0.0, canvasY-canvasX, canvasX, canvasX);
                        gc.drawImage(töötab, 0.0, canvasY-canvasX, canvasX, canvasX);
                        mängija.setTööBool(true);
                        break;
                    case DIGIT5:
                        System.out.println("sa chillid");
                        gc.setFill(Color.SNOW);
                        gc.fillRect(0,0,canvasX, canvasY);
                        gc.drawImage(backgroundKoduPäev, 0.0, canvasY-canvasX, canvasX, canvasX);
                        gc.drawImage(tavaline, 0.0, canvasY-canvasX, canvasX, canvasX);
                        mängija.setTööBool(false);
                        break;
                    case SPACE:
                        if (!mängija.isPaused()) {
                            System.out.println("pausile");
                            mängija.pause();
                        } else {
                            mängija.resume();
                        }
                }
            }
        });


        peaLava.setOnCloseRequest(new EventHandler<WindowEvent>() {
            public void handle(WindowEvent event) {
                // luuakse teine lava
                Stage kusimus = new Stage();
                // küsimuse ja kahe nupu loomine
                Label label = new Label("Kas tõesti tahad kinni panna?");
                Button okButton = new Button("Jah");
                Button cancelButton = new Button("Ei");

                // sündmuse lisamine nupule Jah
                okButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        kusimus.hide();
                        mängija.stop();
                    }
                });

                // sündmuse lisamine nupule Ei
                cancelButton.setOnAction(new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent event) {
                        mängija.resume();
                        peaLava.show();
                        kusimus.hide();
                    }
                });
                // nuppude grupeerimine
                FlowPane pane = new FlowPane(10, 10);
                pane.setAlignment(Pos.CENTER);
                pane.getChildren().addAll(okButton, cancelButton);

                // küsimuse ja nuppude gruppi paigutamine
                VBox vBox = new VBox(10);
                vBox.setAlignment(Pos.CENTER);
                vBox.getChildren().addAll(label, pane);

                //stseeni loomine ja näitamine
                Scene stseen2 = new Scene(vBox);
                kusimus.setScene(stseen2);
                mängija.pause();
                kusimus.show();
            }
        }); //siin lõpeb aknasündmuse kirjeldus



        peaLava.setTitle("Matemaatiku simulaator");  // lava tiitelribale pannakse tekst
        peaLava.setScene(stseen1);  // lavale lisatakse stseen


        peaLava.minWidthProperty().bind(stseen1.heightProperty().divide(2));
        peaLava.minHeightProperty().bind(stseen1.widthProperty().divide(2));
        peaLava.maxWidthProperty().bind(stseen1.heightProperty().multiply(2));
        peaLava.maxHeightProperty().bind(stseen1.widthProperty().multiply(2));

        //et akna suurust saaks normaalselt muuta, ning et elemendid muutuksid koos nendega
        peaLava.widthProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                canvasX = newValue.doubleValue();
                lõuend.setWidth(canvasX);
                ristkulikPudel.setX(canvasX/18);
                ristkulikRaamat.setX(canvasX/3.6);
                ristkulikUni.setX(canvasX/2);
                ristkulikRaha.setX(canvasX/1.4);

                tekstRahulolu.setFont(Font.font("null", FontWeight.BOLD, canvasX/31));
                tekstTarkus.setFont(Font.font("null", FontWeight.BOLD, canvasX/31));
                tekstEnergia.setFont(Font.font("null", FontWeight.BOLD, canvasX/31));
                tekstRaha.setFont(Font.font("null", FontWeight.BOLD, canvasX/31));
            }
        });

        peaLava.heightProperty().addListener(new ChangeListener<Number>() {
            public void changed(ObservableValue<? extends Number> ov, Number oldValue, Number newValue) {
                canvasY = newValue.doubleValue()-25;
                lõuend.setHeight(canvasY);
                ristkulikPudel.setY(canvasY/8);
                ristkulikRaamat.setY(canvasY/8);
                ristkulikUni.setY(canvasY/8);
                ristkulikRaha.setY(canvasY/8);


            }
        });


        peaLava.show();  // lava tehakse nähtavaks


    }

    public static void main(String[] args) {
        launch(args);
    }

}
