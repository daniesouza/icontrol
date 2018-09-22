package br.com.icontrol.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import br.com.icontrol.entity.Log;
import br.com.icontrol.exceptions.ServicoException;
import br.com.icontrol.exceptions.WebException;
import br.com.icontrol.util.Icone;

/**
 * @author Icontrol
 *      
 */

@ManagedBean
@ViewScoped
public class ThemeSwitcherBean extends AbstractViewHelper<Log> implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -2189056391748463284L;

	private List<Icone> listaTemas;  
      
    private String theme;  
  
	@Override
	public void inicializar() throws WebException {
        listaTemas = new ArrayList<Icone>();  
        
        listaTemas.add(new Icone("aristo.png", "aristo"));  
        listaTemas.add(new Icone("black-tie.png", "black-tie"));  
        listaTemas.add(new Icone("blitzer.png", "blitzer"));  
        listaTemas.add(new Icone("bluesky.png", "bluesky"));  
        listaTemas.add(new Icone("casablanca.png", "casablanca"));  
        listaTemas.add(new Icone("cupertino.png", "cupertino"));  
        listaTemas.add(new Icone("dark-hive.png", "dark-hive"));  
        listaTemas.add(new Icone("dot-luv.png", "dot-luv"));  
        listaTemas.add(new Icone("eggplant.png", "eggplant"));  
        listaTemas.add(new Icone("excite-bike.png", "excite-bike"));  
        listaTemas.add(new Icone("flick.png", "flick"));  
        listaTemas.add(new Icone("glass-x.png", "glass-x"));  
        listaTemas.add(new Icone("hot-sneaks.png", "hot-sneaks"));  
        listaTemas.add(new Icone("humanity.png", "humanity"));  
        listaTemas.add(new Icone("le-frog.png", "le-frog"));  
        listaTemas.add(new Icone("midnight.png", "midnight"));  
        listaTemas.add(new Icone("mint-choc.png", "mint-choc"));  
        listaTemas.add(new Icone("overcast.png", "overcast"));  
        listaTemas.add(new Icone("pepper-grinder.png", "pepper-grinder"));  
        listaTemas.add(new Icone("redmond.png", "redmond"));  
        listaTemas.add(new Icone("rocket.png", "rocket"));  
        listaTemas.add(new Icone("sam.png", "sam"));  
        listaTemas.add(new Icone("smoothness.png", "smoothness"));  
        listaTemas.add(new Icone("south-street.png", "south-street"));  
        listaTemas.add(new Icone("start.png", "start"));  
        listaTemas.add(new Icone("sunny.png", "sunny"));  
        listaTemas.add(new Icone("swanky-purse.png", "swanky-purse"));  
        listaTemas.add(new Icone("trontastic.png", "trontastic"));  
        listaTemas.add(new Icone("ui-darkness.png", "ui-darkness"));  
        listaTemas.add(new Icone("ui-lightness.png", "ui-lightness"));  
        listaTemas.add(new Icone("vader.png", "vader"));
		
	}
    
  
    public String getTheme() {  
        return theme;  
    }  
  
    public void setTheme(String theme) {  
        this.theme = theme;  
    }  


	public List<Icone> getListaTemas() {
		return listaTemas;
	}


	public void setListaTemas(List<Icone> listaTemas) {
		this.listaTemas = listaTemas;
	}


	@Override
	public void salvarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void alterarImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluirImpl() throws ServicoException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pesquisar() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub
		
	}  
     
    
 
} 