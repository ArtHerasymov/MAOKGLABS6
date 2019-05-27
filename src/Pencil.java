import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.sun.j3d.utils.universe.SimpleUniverse;
import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

// Vector3f - float, Vector3d - double
public class Pencil implements ActionListener {
    private float upperEye = 5.0f;
    private float fartherEye = 6.0f;

    private TransformGroup treeTransformGroup;
    private TransformGroup viewingTransformGroup;
    private Transform3D treeTransform3D = new Transform3D();
    private Transform3D viewingTransform = new Transform3D();
    private float angle = 0;
    private float height;
    private float distance;
    private boolean descend = true;
    private boolean approaching = true;

    public static void main(String[] args) {
        new Pencil();
    }

    private Pencil() {
        Timer timer = new Timer(50, this);
        SimpleUniverse universe = new SimpleUniverse();

        viewingTransformGroup = universe.getViewingPlatform().getViewPlatformTransform();
        universe.addBranchGraph(createSceneGraph());

        height = upperEye;
        distance = fartherEye;
        timer.start();
    }

    private BranchGroup createSceneGraph() {
        BranchGroup objRoot = new BranchGroup();

        treeTransformGroup = new TransformGroup();
        treeTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        buildCastleSkeleton();
        objRoot.addChild(treeTransformGroup);

        Background background = new Background(new Color3f(5.0f, 5.0f, 5.0f));
        BoundingSphere sphere = new BoundingSphere(new Point3d(0,0,0), 100000);
        background.setApplicationBounds(sphere);
        objRoot.addChild(background);

        BoundingSphere bounds = new BoundingSphere(new Point3d(0.0, 0.0, 0.0),100.0);
        Color3f light1Color = new Color3f(1.0f, 0.5f, 0.4f);
        Vector3f light1Direction = new Vector3f(4.0f, -7.0f, -12.0f);
        DirectionalLight light1 = new DirectionalLight(light1Color, light1Direction);
        light1.setInfluencingBounds(bounds);
        objRoot.addChild(light1);

        Color3f ambientColor = new Color3f(1.0f, 1.0f, 1.0f);
        AmbientLight ambientLightNode = new AmbientLight(ambientColor);
        ambientLightNode.setInfluencingBounds(bounds);
        objRoot.addChild(ambientLightNode);
        return objRoot;
    }


    private void buildCastleSkeleton() {
        Transform3D body2T = new Transform3D();
        body2T.setTranslation(new Vector3f(.0f, .0f, 0.25f));
        TransformGroup body2TG = new TransformGroup();
        body2TG.setTransform(body2T);
        treeTransformGroup.addChild(body2TG);
        setCylinderTowers();
    }


    private void setCylinderTowers(){
        TransformGroup cylinderTower1 = PencilBody.getPencilBody(1.5f,   .5f, .0f);
        treeTransformGroup.addChild(cylinderTower1);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        float delta = 0.03f;

        treeTransform3D.rotZ(angle);
        treeTransformGroup.setTransform(treeTransform3D);
        angle += delta;

        float lowerEye = 1.0f;
        if (height > upperEye){
            descend = true;
        }else if(height < lowerEye){
            descend = false;
        }
        if (descend){
            height -= delta;
        }else{
            height += delta;
        }

        float nearestEyeLimit = 3.0f;
        if (distance > fartherEye){
            approaching = true;
        }else if(distance < nearestEyeLimit){
            approaching = false;
        }
        if (approaching){
            distance -= delta;
        }else{
            distance += delta;
        }

        Point3d eye = new Point3d(distance, distance, height);
        Point3d center = new Point3d(.0f, .0f ,0.5f);
        Vector3d up = new Vector3d(.0f, .0f, 1.0f);
        viewingTransform.lookAt(eye, center, up);
        viewingTransform.invert();
        viewingTransformGroup.setTransform(viewingTransform);
    }
}
