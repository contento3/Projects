package com.contento3.security.role.service.impl;

import java.util.Collection;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.contento3.common.exception.EntityAlreadyFoundException;
import com.contento3.common.exception.EntityCannotBeDeletedException;
import com.contento3.common.exception.EntityNotCreatedException;
import com.contento3.security.role.dao.RoleDao;
import com.contento3.security.role.dto.RoleDto;
import com.contento3.security.role.model.Role;
import com.contento3.security.role.service.RoleAssembler;
import com.contento3.security.role.service.RoleService;
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class RoleServiceImpl implements RoleService{

	private  RoleDao roleDao;
	private RoleAssembler roleAssembler;
	RoleServiceImpl(final RoleDao roleDao,final RoleAssembler roleAssembler)
	{
		this.roleDao = roleDao;
		this.roleAssembler = roleAssembler;
	}
	@RequiresPermissions("ROLE:ADD")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Integer create(RoleDto dto) throws EntityAlreadyFoundException,
			EntityNotCreatedException {
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
	@RequiresPermissions("ROLE:DELETE")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void delete(RoleDto dtoToDelete)
			throws EntityCannotBeDeletedException {
		// TODO Auto-generated method stub
		roleDao.delete(roleAssembler.dtoToDomain(dtoToDelete));
	}
	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<RoleDto> findRolesByAccountId(Integer accountId) {
		// TODO Auto-generated method stub
		return roleAssembler.domainsToDtos(roleDao.findRolesByAccountId(accountId));
		
	}
	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public RoleDto findRoleByName(String name) {
		// TODO Auto-generated method stub
		return roleAssembler.domainToDto(roleDao.findByRolename(name));
		
		
	}

/*public Collection<RoleDto> type;
	
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public Collection<RoleDto> findAllRoles(){
		return RoleAssembler.domainsToDtos(RoleDao.findAll());
	}
	*/
	@RequiresPermissions("ROLE:EDIT")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	@Override
	public void update(RoleDto dtoToUpdate) {
		// TODO Auto-generated method stub
		roleDao.update( roleAssembler.dtoToDomain(dtoToUpdate) );
	}
	@RequiresPermissions("ROLE:VIEW")
	@Override
	public Collection<RoleDto> findAllRoles() {
		// TODO Auto-generated method stub
		return null;
	}
	@RequiresPermissions("ROLE:VIEW")
	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	@Override
	public RoleDto findById(Integer id) {
		// TODO Auto-generated method stub
		return roleAssembler.domainToDto(roleDao.findById(id));
	}
	@RequiresPermissions("ROLE:VIEW")
	@Override
	public Collection<RoleDto> findRolesByGroupId(Integer Id) {
		// TODO Auto-generated method stub
		return roleAssembler.domainsToDtos(roleDao.findRolesByGroupId(Id));
	}
	
}
