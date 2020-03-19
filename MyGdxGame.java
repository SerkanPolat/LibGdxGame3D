package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.PointLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.ui.TextArea;

public class MyGdxGame extends ApplicationAdapter{
	private PerspectiveCamera kamera; 
	ModelBatch modelBatch; 
	ModelBuilder modelBuilder;
	Model model1,ModelIsik;
	YeryuzuParcasi y;
	Vector3 KameraYonY;
	float KameraX,KameraY,KameraZ,KameraAci;
	Environment cevre;
	PointLight NoktasalIsik1;
	ModelInstance modelIsikKaynak; 
	@Override
	public void create () {
		KameraYonY = new Vector3(0F,0,-0.2F);
		KameraX = 7F;
		KameraY = 1F;
		KameraZ = 0f;
		KameraAci=60;
		kamera = new PerspectiveCamera(KameraAci,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		kamera.position.set(KameraX, KameraY,KameraZ);
		kamera.lookAt(KameraX,1f,1);
		kamera.near = 0.1F;
		kamera.far = 25f;

		modelBatch = new ModelBatch();
		modelBuilder = new ModelBuilder();
		cevre = new Environment();
		cevre.set(new ColorAttribute(ColorAttribute.Fog,0.15f, 0.15f, 0.15f, 1f));
		ModelIsik = modelBuilder.createSphere(0.1F, 0.1F, 0.1F, 15, 15, new Material(ColorAttribute.createDiffuse(Color.WHITE)), 
				VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal); //ModelBuilder ile Model Planlari Olusturuyor.
		int s = 15;
		NoktasalIsik1 = new PointLight();
		NoktasalIsik1.set(1f, 1f, 1f, KameraX, KameraY+1, KameraZ, 30f);
		modelIsikKaynak = new ModelInstance(ModelIsik,KameraX,KameraY+1,KameraZ);
		cevre.add(NoktasalIsik1);

		cevre.set(new ColorAttribute(
				ColorAttribute.AmbientLight,
				0.3f,0.3f,0.3f,1f)); 

		y = new YeryuzuParcasi(modelBuilder);
	}
	float skor=0;
	@Override
	public void render () {

		Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT); 
		kamera.update();
		modelBatch.begin(kamera);
		
		y.YeryuzunuCiz(modelBatch,cevre,kamera.position.z);
		
		modelBatch.render(modelIsikKaynak);
		
		CarpmaKontrolcusu();
		modelBatch.end();

		
	}
	
	private void CarpmaKontrolcusu() {
		
		y.EtkinEngelleriBelirle((int)kamera.position.z);
		if(y.CarpmaKontrolu((int)kamera.position.x, (int)kamera.position.z)) {
		//	System.out.println("CARPTIN");
			
		}
		kamera.translate(-KameraYonY.x,-KameraYonY.y,-KameraYonY.z);
		if(kamera.position.x<0) {
			kamera.translate(KameraYonY.x,-KameraYonY.y,-KameraYonY.z);
		}
		else if(kamera.position.x>15) {
			kamera.translate(KameraYonY.x,-KameraYonY.y,-KameraYonY.z);
		}
		NoktasalIsik1.set(1f, 1f, 1f, kamera.position.x, kamera.position.y+1, kamera.position.z, 30f);
		
		
	}

	@Override
	public void dispose () {
	}
}
