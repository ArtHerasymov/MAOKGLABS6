import java.awt.Container;
import javax.media.j3d.*; // for transform
import javax.vecmath.Color3f;
import java.awt.Color;
import com.sun.j3d.utils.geometry.*;
import javax.vecmath.*; // for Vector3f
import com.sun.j3d.utils.image.TextureLoader;

class PencilBody {


    private static Cylinder getCentralTower(float cylinderHeight) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cylinder(0.1f, cylinderHeight, primflags, getPencilBodyAppearence());
    }

    private static Cylinder getEraser(float cylinderHeight) {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cylinder(0.1f, cylinderHeight, primflags, getEraserAppearence());
    }

    private static Cone getCentralTowerRoof() {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cone(0.1f, 0.3f, primflags, getPencilEdgeAppearence());
    }

    private static Cone getPencilEdge() {
        int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
        return new Cone(0.1f, 0.5f, primflags, getPencilGraphiteAppearence());
    }

    static TransformGroup getPencilBody(float height, float xPos, float yPos){
        TransformGroup tg = new TransformGroup();

        Cylinder centralTower = PencilBody.getCentralTower(height);
        Transform3D centralTowerT = new Transform3D();
        centralTowerT.setTranslation(new Vector3f(xPos, yPos, height*0.5f));
        centralTowerT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup centralTowerTG = new TransformGroup();
        centralTowerTG.setTransform(centralTowerT);
//        Cylinder eraser = PencilBody.getEraser(0.1f);
//        centralTowerTG.addChild(eraser);
        centralTowerTG.addChild(centralTower);
        tg.addChild(centralTowerTG);

        Cone centralTowerRoof = PencilBody.getCentralTowerRoof();
        Cone pencilEdge = PencilBody.getPencilEdge();
        Transform3D centralTowerRoofT = new Transform3D();
        centralTowerRoofT.setTranslation(new Vector3f(xPos, yPos, height+0.15f));
        centralTowerRoofT.setRotation(new AxisAngle4d(1, 0, 0, Math.toRadians(90)));
        TransformGroup centralTowerRoofTG = new TransformGroup();
        centralTowerRoofTG.setTransform(centralTowerRoofT);
        centralTowerRoofTG.addChild(centralTowerRoof);
        centralTowerRoofTG.addChild(pencilEdge);
        tg.addChild(centralTowerRoofTG);

        return tg;
    }
    private static Appearance getPencilEdgeAppearence() {
        Appearance ap = new Appearance();
        Color3f emission = new Color3f(new Color(0,0, 0));
        Color3f ambient = new Color3f(new Color(100, 88, 59));
        Color3f diffuse = new Color3f(new Color(255, 156, 41));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emission, diffuse, specular, 1.0f));
        return ap;
    }

    private static Appearance getPencilGraphiteAppearence() {
        Appearance ap = new Appearance();
        Color3f emissive = new Color3f(new Color(0,0, 0));
        Color3f ambient = new Color3f(new Color(0, 0, 0));
        Color3f diffuse = new Color3f(new Color(0, 0, 0));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }


    private static Appearance getPencilBodyAppearence() {
        TextureLoader loader = new TextureLoader("source_folder\\pencilbody.jpg", "LUMINANCE", new Container());
        Texture texture = loader.getTexture();
        
        texture.setBoundaryModeS(Texture.WRAP);
        texture.setBoundaryModeT(Texture.WRAP);
        texture.setBoundaryColor(new Color4f(0.0f, 1.0f, 1.0f, 0.0f));

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);

        Appearance ap = new Appearance();
        ap.setTexture(texture);
        ap.setTextureAttributes(texAttr);

        Color3f emissive = new Color3f(new Color(0,0, 0));
        Color3f ambient = new Color3f(new Color(100,38, 38));
        Color3f diffuse = new Color3f(new Color(100,38, 38));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }

    private static Appearance getEraserAppearence() {

        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.BLEND);

        Appearance ap = new Appearance();

        Color3f emissive = new Color3f(new Color(0,0, 0));
        Color3f ambient = new Color3f(new Color(100, 87, 0));
        Color3f diffuse = new Color3f(new Color(100,38, 38));
        Color3f specular = new Color3f(new Color(0,0, 0));
        ap.setMaterial(new Material(ambient, emissive, diffuse, specular, 1.0f));
        return ap;
    }
}
