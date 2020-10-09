package edu.eci.cvds.sampleprj.dao.mybatis;

import java.util.List;

import com.google.inject.Inject;

import edu.eci.cvds.sampleprj.dao.PersistenceException;
import edu.eci.cvds.sampleprj.dao.TipoItemDAO;
import edu.eci.cvds.sampleprj.dao.mybatis.mappers.TipoItemMapper;
import edu.eci.cvds.samples.entities.TipoItem;

public class MyBATISTipoItemDAO implements TipoItemDAO{
	
	@Inject
	private TipoItemMapper tipoItemMapper;
	
	@Override
	public void insertarTipoItem(TipoItem tipoItem)throws PersistenceException{
		try {
			tipoItemMapper.insertarTipoItem(tipoItem);
		}catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("No se puede añadir el tipo de item"+tipoItem.toString(), e);
		}
	}
	@Override
	public TipoItem consultarTipoItems(int id)throws PersistenceException{
		try {
			return tipoItemMapper.getTipoItem(id);
		}catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("No se puede consultar el tipo del item"+id, e);
		}
	}
	@Override
	public List<TipoItem> consultarTipoItems()throws PersistenceException{
		try {
			return tipoItemMapper.consultarTiposItems();
		}catch (org.apache.ibatis.exceptions.PersistenceException e) {
			throw new PersistenceException("No se puede consultar todos los items", e);
		}
	}

}
