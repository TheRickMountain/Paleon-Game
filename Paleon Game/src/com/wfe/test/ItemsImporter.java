package com.wfe.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.wfe.graph.Texture;
import com.wfe.graph.TextureLoader;
import com.wfe.gui.Item;

public class ItemsImporter {
	
	private static final String PATH = "/items";
	
	public void importItems(Map<String, Item> items) throws Exception {
		List<String> names = new ArrayList<String>();
		
		URL url =  ItemsImporter.class.getResource(PATH);
		if(url != null) {
			try {
				File apps = new File(url.toURI());
				for(File app : apps.listFiles()) {
					names.add(app.getName());
				}
			} catch (URISyntaxException ex) {}
		}
		
		BufferedReader reader = null;
		for(int i = 0; i < names.size(); i+=2) {
			Texture icon = TextureLoader.load(PATH + "/" + names.get(i));
			reader = new BufferedReader(new InputStreamReader(
					ItemsImporter.class.getResourceAsStream(PATH + "/" + names.get(i + 1))));
						
			String[] parameters = reader.readLine().split("\\;");
			
			items.put(parameters[0], 
					new Item(
							icon, 
							parameters[0], 
							Integer.parseInt(parameters[1]), 
							parameters[2], 
							Integer.parseInt(parameters[3]), 
							Integer.parseInt(parameters[4]), 
							Item.Type.valueOf(parameters[5]), 
							Integer.parseInt(parameters[6])));
			reader.close();
		}
		
	}
	
}
