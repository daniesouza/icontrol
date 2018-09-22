package br.com.icontrol.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import br.com.icontrol.entity.Arduino;
import br.com.icontrol.entity.Comando;
import br.com.icontrol.entity.Componente;
import br.com.icontrol.entity.Grupo;
import br.com.icontrol.entity.GrupoArdCompPermissoes;
import br.com.icontrol.entity.Usuario;

/**
 * @author Icontrol
 * 
 */

public class PermissaoUtil {

	public static boolean existePermissao(Usuario usuario,Arduino arduino, Componente componente, Comando comando) {
		boolean retorno = false;
		List<Grupo> grupos = usuario.getGrupos();
		for (Grupo grupo : grupos) {
			List<GrupoArdCompPermissoes> grupoArdCompPermissoes = grupo.getGruposArdCompPermissoes();
			for (GrupoArdCompPermissoes grupoArdCompPermissao : grupoArdCompPermissoes) {
						
				Arduino arduinoUsuario = grupoArdCompPermissao.getArduino();
				
				if(arduinoUsuario.equals(arduino)){
					
					Componente componenteUsuario = grupoArdCompPermissao.getComponente();
				
					if(componenteUsuario.equals(componente)) {
						retorno = grupoArdCompPermissao.getComando().equals(comando) || retorno;
					}
				}				
			}
		}
		return retorno;
	}
	
	
	public static Comando obterComando(Collection<Comando> comandos, String codigoComando) {
		Comando comandoRetorno = null;
		for (Comando comando : comandos) {
			if (comando.getCodigo().equalsIgnoreCase(codigoComando)) {
				comandoRetorno = comando;
				break;
			}
		}
		return comandoRetorno;
	}

	public static List<Componente> obterComponentes(List<Grupo> grupos) {
		List<Componente> componentes = new ArrayList<Componente>();
		for (Grupo grupo : grupos) {
			List<GrupoArdCompPermissoes> grupoArdCompPermissoes = grupo.getGruposArdCompPermissoes();
			for (GrupoArdCompPermissoes grupoArdCompPermissao : grupoArdCompPermissoes) {
				Componente componente = grupoArdCompPermissao.getComponente();
				if(!componentes.contains(componente)) {
					componentes.add(componente);
				}
			}
		}
		Collections.sort(componentes);
		return componentes;
	}
	
	public static List<Componente> obterComponentes(Grupo grupo) {
		List<Grupo> grupos = new ArrayList<Grupo>();
		grupos.add(grupo);
		List<Componente> componentes = obterComponentes(grupos);
		return componentes;
	}
	
	public static boolean isAdmin(Usuario usuario) {
		boolean isAdmin = false;
		List<Grupo> grupos = usuario.getGrupos();
		for (Grupo grupo : grupos) {
			isAdmin = grupo.isAdministrador() ? grupo.isAdministrador() : isAdmin;
		}
		return isAdmin;
	}
}
