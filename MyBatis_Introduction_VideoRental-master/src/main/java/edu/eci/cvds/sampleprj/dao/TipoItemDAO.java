package edu.eci.cvds.sampleprj.dao;

import java.util.List;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemDAO {
	public void insertarTipoItem(TipoItem tipoItem) throws PersistenceException;

	public TipoItem consultarTipoItems(int id) throws PersistenceException;

	public List<TipoItem> consultarTipoItems() throws PersistenceException;

}
