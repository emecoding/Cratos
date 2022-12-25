# Cratos

If interested, download the Cratos.jar file to start.
Tutorials coming later!


Example application with Cratos:

    import com.cratos.Cratos;
    import com.cratos.engineResource.Scene;
    import com.cratos.engineSystem.SceneManager;
    import com.cratos.entity.Entity;
    import com.cratos.entity.component.Camera;
    import com.cratos.window.Window;
    
    public class Game
    {
        public static void main(String[] args)
        {
            Window window = Cratos.CreateWindow(1280, 720, "Game"); //Create the main window
            Cratos.InitializeCratos(); //Initialize cratos
            SceneManager sceneManager = Cratos.CreateSceneManager(); //Create the default scene manager

            Scene mainScene = sceneManager.AddScene("Main"); //Create the main scene
            Entity MainCamera = mainScene.AddEntity("MainCamera"); //Create, and add, a entity to the current scene, which name is MainCamera. This entity will hold the camera component
            MainCamera.AddComponent(new Camera()); //Add the camera component to the MainCamera entity


            Cratos.CreateRenderer(); //Create the default renderer
            Cratos.CreatePhysicsEngine(); //Create the default physics engine

            Cratos.Run(); //Run the default cratos application
            Cratos.Terminate(); //After running the application, terminate it so it shuts down properly
        }
    }
