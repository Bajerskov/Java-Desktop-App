//import javafx.scene.control.*;
import javafx.application.Application;
import javafx.scene.Scene;

import javafx.scene.layout.*;
import javafx.stage.Stage;

import view.*;

public class StartApplication extends Application{

    private Stage primaryStage;
    private BorderPane rootLayout;

	public static void main(String[] args){
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Ulla T's Moneygrabber 2000");

		initRootLayout();
	}


	public void initRootLayout(){

			rootLayout = new BorderPane();
			MainController test = new MainController(rootLayout);

			Scene scene = new Scene(rootLayout);

			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public Stage getPrimaryStage(){
		return primaryStage;
	}
}