package co.edu.uniquindio.billetera.billeteravirtual.patrones.creacionales.factoryMethod;

import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.dto.UsuarioDto;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.mapping.mappers.BilleteraVirtualMappingImpl;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.service.IBilleteraVirtualMapping;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.service.IModelFactoryService;
import co.edu.uniquindio.billeteravirtual.billetera_virtual.utils.DataUtil;
import java.util.List;

public class ModelFactory implements IModelFactoryService {
    private static ModelFactory modelFactory;
    private BilleteraVirtualObjeto billeteraVirtualObjeto;
    private IBilleteraVirtualMapping mapper;

    public static ModelFactory getInstancia() {
        if(modelFactory == null) {
            modelFactory = new ModelFactory();
        }
        return modelFactory;
    }

    private ModelFactory(){
        mapper = new BilleteraVirtualMappingImpl();
        billeteraVirtualObjeto = DataUtil.inicializarDatos();
    }

    @Override
    public List<UsuarioDto> obtenerUsuarios() {
        return mapper.getUsuariosDto(billeteraVirtualObjeto.getListaUsuarios());
    }
}