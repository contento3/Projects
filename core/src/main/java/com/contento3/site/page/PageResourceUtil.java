package com.contento3.site.page;

import com.contento3.cms.page.template.model.SystemTemplateNameEnum;

public class PageResourceUtil {

		public static final String PAGE = "page";
		public static final String ARTICLE = "article";
		public static final String BLOG = "blog";
		public static final String STORY = "story";
		public static final String UNKNOWN = "unknown";
		public static final String TEMPLATE = "template";
		public static final String GLOBAL_TEMPLATE = "global";

		/**
		 * Returns the resource type name based on the url
		 * @return
		 */
		public static String fetchTemplateResourceType(final String resourceName){
			if (resourceName.startsWith("article/") || resourceName.startsWith("blog/") || resourceName.startsWith("story/")){
				return ARTICLE;
			}
			else if (resourceName.equals(SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS.getValue())) {
				return SystemTemplateNameEnum.SYSTEM_REGISTER_SUCCESS.toString();
			}
			else if (resourceName.startsWith("/template") || resourceName.startsWith("template") || resourceName.startsWith("/template_key") || resourceName.startsWith("template_key") ) {
				return TEMPLATE;
			}
			else return PAGE;
		}
}
