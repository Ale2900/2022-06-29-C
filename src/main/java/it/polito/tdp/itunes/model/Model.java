package it.polito.tdp.itunes.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.itunes.db.ItunesDAO;

public class Model {
	
	//USA SEMPRE QUESTO MODO DI INIZIALIZZARE IL MODEL E IL GRAFO
	private ItunesDAO dao;
	private Graph <Album, DefaultWeightedEdge> grafo;
	List<Album> albums;
	
	private Map<Integer, Album> albumIdMap;
	
	public Model()
	{
		this.dao=new ItunesDAO();
		this.albumIdMap=new HashMap<Integer, Album>();
		List<Album> allAlbums=this.dao.getAllAlbums();
		
		//setto la mappa
		for(Album a: allAlbums) {
			this.albumIdMap.put(a.getAlbumId(), a);
		}
	}
	
	public void creaGrafo(Double prezzo ) {
		this.grafo=new SimpleWeightedGraph<Album, DefaultWeightedEdge>(DefaultWeightedEdge.class);
		
		this.albums=this.dao.getAlbumsByPrice(prezzo, albumIdMap);
		//creo i vertici
		List<Album> vertici=this.dao.getAlbumsByPrice(prezzo, albumIdMap);
		
		Graphs.addAllVertices(this.grafo, vertici);
		
		
		
		
	//provo a settare gli archi senza fare un'altra query, quindi calcolo la differenza tra il peso degli archi e se questa è diversa da 0 aggiungo un arco
	//questo lo posso fare perchè nel risultato della query per gli archi ho già tutte le informazioni che mi servono per creare gli archi
		Double differenza=0.0;
		for(Album a1: this.grafo.vertexSet()) {
			for(Album a2: this.grafo.vertexSet()) {
				
				if(!a1.equals(a2)) {
				differenza=a1.getPrezzo()-a2.getPrezzo();
				if(differenza!=0) {
					if(differenza>0) {
						Graphs.addEdge(this.grafo, a1, a2, differenza);
					} else if(differenza<0) {
						Graphs.addEdge(this.grafo, a1, a2, -differenza); //perche il testo mi dice che il peso dell'arco è il valore assoluto della differenza
						//avrei potuto anche creare l'arco in un altro modo
						//trovo chi ha il peso maggiore e sottraggo il secondo a questo
					}
				}
			}
		}}
		
		
		
	
	}
	
	public int nVertici() {
		return this.grafo.vertexSet().size();
	}
	
	public int nArchi() {
		return this.grafo.edgeSet().size();
	}
	
	//la lista mi serve per popolare la tendina da cui l'utente sceglie un vertice
	public List<Album> getAlbums(){
		return albums;
	}
	
	
	//per poter stampare un bilancio devo prima calcolare il bilancio di ognuno dei vertici
	public Double getBilancio(Album a) {
		//QUI IL BILANCIO DI UN VERTICE E' DEFINITO COME LA MEDIA DI TUTTI GLI ARCHI INCIDENTI SU DI ESSO
		
		//faccio la lista di adiacenti, prendo ogni arco e faccio la media
		List<Album> adiacenti=Graphs.neighborListOf(this.grafo, a);
		Double somma=0.0;
		Double bilancio=0.0;
		for(Album adiacente: adiacenti) {
			somma=somma+ this.grafo.getEdgeWeight(this.grafo.getEdge(a, adiacente));
		}
		bilancio=somma/adiacenti.size();
		
		return bilancio;
	}
	
	public List<AlbumBilancio> getBilanci(Album scelto){
		List<AlbumBilancio> result=new ArrayList<AlbumBilancio>();
		
		List<Album> adiacenti=Graphs.neighborListOf(this.grafo, scelto);
		
		for(Album a: adiacenti) {
			result.add(new AlbumBilancio(a, getBilancio(a)));
			
		}
		
		Collections.sort(result);
		
		return result;
	}
	
	
}
