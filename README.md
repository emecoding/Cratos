# Cratos
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


Find the .jar file from out/artifacts/Cratos_jar/Cratos.jar
No support for gradle or maven yet!

Cratos Functions:
    `Window Cratos.CreateWindow(int width, int height, String title) //Creates the main window for the application. Is not voluntary.`

    SceneManager Cratos.CreateSceneManager() //Create the default scene manager.
    SceneManager Cratos.CreateSceneManager(SceneManager manager) //Create your own scene manager.
    
    Renderer Cratos.CreateRenderer() //Create the default scene manager.
    Renderer Cratos.CreateRenderer(Renderer renderer) //Create your own renderer.

    PhysicsEngine Cratos.CreatePhysicsEngine() //Create the default physics engine.
    PhysicsEngine Cratos.CreatePhysicsEngine(PhysicsEngine engine) //Create your own physics engine.

    InputManager Cratos.CreateInputManager() //Create the default input manager.
    InputManager Cratos.CreateInputManager(InputManager manager) //Create your own input manager.

    Cursor Cratos.CreateCursor() //Create the default Cursor.
    Cursor Cratos.CreateCursor(Cursor cursor) //Create your own cursor.

    Debug Cratos.CreateDebug() //Create the default Debug(ger).
    Debug Cratos.CreateDebug(Debug debug) //Create your own debug

    void Update() //Update every entity
    
    void Start() //Calls the entity.component.Start() once before the main loop.
    
    void Run() //The main loop. Same as:
        while(!Cratos.CurrentWindow().ShouldClose())
        {
            Cratos.CurrentWindow().Clear();
            CratosCursor.Update();
            if(CratosPhysicsEngine != null) CratosPhysicsEngine.Simulate();
            CratosRenderer.RenderCurrentScene();
            Cratos.Update();
            Cratos.CurrentWindow().SwapBuffersAndPollEvents();
        }