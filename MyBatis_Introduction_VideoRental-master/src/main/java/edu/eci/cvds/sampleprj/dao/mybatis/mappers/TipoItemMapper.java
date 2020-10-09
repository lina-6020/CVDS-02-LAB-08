package edu.eci.cvds.sampleprj.dao.mybatis.mappers;


import java.util.List;
import org.apache.ibatis.annotations.Param;

import edu.eci.cvds.samples.entities.TipoItem;

public interface TipoItemMapper {
    
    
    public List<TipoItem> consultarTiposItems();
    
    public TipoItem getTipoItem(int id);
    
    public void insertarTipoItem(@Param ("tipo")TipoItem tipoItem);

}
