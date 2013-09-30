package com.contento3.security.role.service.impl;

import java.util.Collection;

import org.apache.commons.lang.Validate;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.role.dao.RoleDao;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;
import com.contento3.security.role.service.RoleAssembler;
import com.contento3.security.role.service.RoleService;

public class RoleServiceImpl implements RoleService{

	private  RoleDao roleDao;
	private RoleAssembler roleAssembler;
	RoleServiceImpl(final RoleDao roleDao,final RoleAssembler roleAssembler)
	{
		Validate.notNull(roleDao,"roleDao cannot be null");
		Validate.notNull(roleAssembler,"roleAssembler cannot be null");
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
	}
	@Override
	public Integer create(RoleDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
		Validate.notNull(dto,"dto cannot be null");
		// TODO Auto-generated method stub
		final Role role = roleAssembler.dtoToDomain(dto);
	//	final RandomNumberGenerator saltGenerator = new SecureRandomNumberGenerator();
		//final String salt = saltGenerator.nextBytes().toBase64();
	//	final ByteSource originalPassword = ByteSource.Util.bytes(dto.getPassword());
		
		/*final String rolename=dto.getroleName().toString();
		final String roledesc = dto.getRoleDesc();
		final Integer roleid = dto.getroleid();
		final String account = dto.getAccount().toString(); 
		role.setroleName(rolename);
		role.setDescription(roledesc);
		role.setRoleId(roleid);
	//	role.setAccount(account);*/
		
		int roleName = roleDao.persist(role);
		
//	//	if (null==name){
			//throw new EntityNotCreatedException();
		//}
		return roleName;
	}

	@Override
	public void delete(RoleDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToDelete,"dtoToDelete cannot be null");
		roleDao.delete(roleAssembler.dtoToDomain(dtoToDelete));
	}

	@Override
	public Collection<RoleDto> findRolesByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		Validate.notNull(accountId,"accountId cannot be null");
		return roleAssembler.domainsToDtos(roleDao.findRolesByAccountId(accountId));
		
	}

	@Override
	public RoleDto findRoleByName(String name) {
		// TODO Auto-generated method stub
		Validate.notNull(name,"name cannot be null");
		return roleAssembler.domainToDto(roleDao.findByRolename(name));
		
		
	}

/*public Collection<RoleDto> type;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<RoleDto> findAllRoles(){
		return RoleAssembler.domainsToDtos(RoleDao.findAll());
	}
	*/
	@Override
	public void update(RoleDto dtoToUpdate) {
		// TODO Auto-generated method stub
		Validate.notNull(dtoToUpdate,"dtoToUpdate cannot be null");
		roleDao.update( roleAssembler.dtoToDomain(dtoToUpdate) );
	}
	@Override
	public Collection<RoleDto> findAllRoles() {
		// TODO Auto-generated method stub
		//Validate.notNull(dto,"dto cannot be null");
		return null;
	}
	@Override
	public RoleDto findById(Integer id) {
		// TODO Auto-generated method stub
		Validate.notNull(id,"id cannot be null");
		return roleAssembler.domainToDto(roleDao.findById(id));
	}
	
}
