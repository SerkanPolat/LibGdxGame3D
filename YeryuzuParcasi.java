package com.mygdx.game;

import java.util.Random;

import com.badlogic.gdx.Gdx; 
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.model.MeshPart;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.graphics.g3d.utils.shapebuilders.BoxShapeBuilder;
import com.badlogic.gdx.math.Vector3;

public class YeryuzuParcasi {
	Model modelYeryuzuParca,modelYeryuzuParca2,modelBlokPlan;
	ModelInstance modelInstanceYeryuzuParca,modelInstanceYeryuzuParca2;
	ModelInstance[] modelInstanceBinalar;
	MeshPartBuilder meshPartBuilder;
	ModelBuilder modelBuilder;
	MeshPartBuilder eskiMesh;
	float [][] grid;
	int binaSayisi;
	
	int meshParcaGenislik;
	
	boolean KutuBox1[][];
	boolean KutuBox2[][];
	boolean EtkinYeryuzuEngelleri[][];
	boolean EtkinYeryuzu1;

	Color renk;
	int Parca1Baslama;
	int Parca2Baslama;
	int EtkinBaslama;
	public YeryuzuParcasi(ModelBuilder modelBuilder) {
		Parca1Baslama = 0;
		Parca2Baslama = 0;
		KutuBox1 = new boolean[15][50];
		KutuBox2 = new boolean[15][50];
		EtkinYeryuzu1 = true;
		this.modelBuilder = modelBuilder;
		binaSayisi = 0;
		modelInstanceYeryuzuParca2 = new ModelInstance(new Model());
		
		modelYeryuzuParca = YeryuzuOlustur(0);
		modelInstanceYeryuzuParca = new ModelInstance(modelYeryuzuParca,0f,0f,0f);
		
	}
	
	public void EtkinEngelleriBelirle(int OyuncuZ) {
		
		if((OyuncuZ/50)%2==0) {
			
			EtkinYeryuzuEngelleri = KutuBox1;
				EtkinBaslama = Parca1Baslama;
				
		}else {
			
			EtkinYeryuzuEngelleri = KutuBox2;
			EtkinBaslama = Parca2Baslama;
			
		}
	}
	public boolean CarpmaKontrolu(int OyuncuX,int OyuncuZ) {
		    return EtkinYeryuzuEngelleri[OyuncuX][OyuncuZ-EtkinBaslama];
	}
	
	int index = 1;
	public void YeryuzunuCiz(ModelBatch modelBatch,Environment cevre,float OyuncuKordinat) {

		if(OyuncuKordinat>index*25) {
				        	 if((index*25/50)%2!=0) {
				        		 renk = Color.CYAN;
				        		 EtkinYeryuzu1 = true;
				        		 modelYeryuzuParca = YeryuzuOlustur(25*index+25);
							
							modelInstanceYeryuzuParca = new ModelInstance(modelYeryuzuParca,0f,0f,0f);
				        	 }else {
				        		 renk = Color.RED;
				        		 EtkinYeryuzu1 = false;	
				        		 modelYeryuzuParca2 = YeryuzuOlustur(25*index+25);
									
									modelInstanceYeryuzuParca2 = new ModelInstance(modelYeryuzuParca2,0f,0f,0f);
				        	 }
				        	 index=index+2;
		}
		
		modelBatch.render(modelInstanceYeryuzuParca,cevre);
		modelBatch.render(modelInstanceYeryuzuParca2,cevre);
		
		
		
		for(int i = 0 ;i<binaSayisi;i++) {
			modelBatch.render(modelInstanceBinalar[i],cevre);
		}
		
		
	}
	
	public Model YeryuzuOlustur(int BaslamaNoktasi) {
		int s=0;
		modelBuilder.begin();
		meshPartBuilder = modelBuilder.part("Ucgen", GL20.GL_TRIANGLES, Usage.Position | Usage.Normal| Usage.ColorUnpacked,new Material(ColorAttribute.createDiffuse(Color.ORANGE)));
		Random rnd = new Random();
		meshPartBuilder.setColor(new Color(rnd.nextInt()));
		
		if(EtkinYeryuzu1) {
			KutuBox1 = new boolean[15][50];
			
		}else {
			KutuBox2 = new boolean[15][50];
		}
		
		for(int i = 0;i<15;i++) {
			for(s=BaslamaNoktasi;s<BaslamaNoktasi+50;s++) {

				
				if(rnd.nextInt()%10>=9) {
					if(BaslamaNoktasi!=0) {
						BoxShapeBuilder.build(meshPartBuilder,(float)(2*i+1)/2,(float)0.7,(float)(2*s+1)/2, 1, (float)1.7, (float)1);
						
						if(EtkinYeryuzu1) {
							Parca1Baslama = BaslamaNoktasi;
							KutuBox1[i][s-BaslamaNoktasi] = true;
						}else {
							Parca2Baslama = BaslamaNoktasi;
							KutuBox2[i][s-BaslamaNoktasi] = true;
						}
					}else {
							meshPartBuilder.rect(
								 (float)(i+i+1)/2,0,s+1,
								 i,0,s,
								 i+1,0,s,
						 		 i,0,s,
						 		 0,1,0);
					}
				
				}else {
					meshPartBuilder.rect(
							
							 (float)(i+i+1)/2,0,s+1,
							 i,0,s,
							 i+1,0,s,
					 		 i,0,s,
					 		 0,1,0);
				}
			}
		}
		
		
		return modelBuilder.end();
	}
	
}
