--content type
--====================
--content_Type_id		content_type_name		    is_composed		is_default       description
--====================================================================================================
--		1				text				           	0				0				a component
--		2				image		   		           	0				0				a component
--		3				video		   			   		0				0				a component	
--		4 				document	   			   		0				0				a component
--    	5				url								0				0				a component
--		6				standard article				1				1				standard article
--		7				date							0				0				a date component



--content type and sites/organisation mapping
--many to many relationship
content_type_id        site_id
====================================== 
--		6				1
--		6				2


--content_type_instance:Association of content type to thier instances
--==============================================================================================
--content_type_instance_id	content_type	  
--      1 			      6	
--		2			      7
-- 		3			      6


--content_type_templates:Definition for content types
--====================
--content_Type_tempaltes_id	content_type_id		component_content_Type	order	
--======================================================================================
--					1			1		   			null           		null			
--					2			2		   			null           		null
--					3			3		   			null	   			null
--					4 			4	   				null	   			null
--					5			6					1					1
--					6			6					2					2
--					7			6					3					3
--					8			6					1					4				
	

--content:content storage
content_type_template_id: identifies which component of a content_Type this value belongs to
content_type_instance_id: identifies which instance of this content_Type this value belongs to
--=================================================================================================
--content_id	  content_type_template_id          content_type_instance_id  	value		
--=================================================================================================
--	1	 	       1	       									1	


content_ref:content refernces to other reference
==============================================================================================
content_ref_id		content_id    		content_id_ref_to
===============================================================================================
    1					1		  				3
			
    			