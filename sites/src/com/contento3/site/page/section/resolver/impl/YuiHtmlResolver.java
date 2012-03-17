package com.contento3.site.page.section.resolver.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Collection;
import java.util.Iterator;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.log4j.Logger;

import com.contento3.cms.page.section.dto.PageSectionDto;
import com.contento3.cms.page.template.dto.PageTemplateDto;
import com.contento3.cms.page.template.dto.TemplateDto;
import com.contento3.cms.page.template.service.TemplateService;
import com.contento3.site.page.section.resolver.HtmlResolver;

public class YuiHtmlResolver implements HtmlResolver {

	private static final Logger LOGGER = Logger.getLogger(YuiHtmlResolver.class);

	private TemplateService templateService;
	
	@Override
	public String resolve(final StringBuffer sectionMarkUp, final PageSectionDto dto) {
		String sectionTypeName = dto.getSectionTypeDto().getName();
		return dto.getTemplateMarkup().replace(sectionTypeName.toLowerCase(), sectionMarkUp);
	}

	@Override
	public String resolveBody(final Collection<PageTemplateDto> pageTemplateDtos,
			final PageSectionDto dto) {
		OutputStream out = new ByteArrayOutputStream();

		String bodyTemplateMarkUp = dto.getTemplateMarkup();
		Iterator <PageTemplateDto> pageTemplateIterator = pageTemplateDtos.iterator();
		XMLStreamWriter xmlWriter=null;
		InputStream in = new ByteArrayInputStream(bodyTemplateMarkUp.getBytes());
	      XMLInputFactory factory = XMLInputFactory.newInstance();
	      try {
			XMLEventReader eventReader = factory.createXMLEventReader(in);
			XMLOutputFactory writeFactory = XMLOutputFactory.newInstance();
			xmlWriter = writeFactory.createXMLStreamWriter(out);
			
			while (eventReader.hasNext()) {
				XMLEvent event = eventReader.nextEvent();
				if (event.isStartElement()) {
					StartElement startElement = event.asStartElement();
					xmlWriter.writeStartElement(startElement.getName().getLocalPart());
				}
				
				else if (event.isCharacters()){
					if (event.toString().trim().length()>0){
						if (pageTemplateIterator.hasNext()){
							PageTemplateDto pageTemplateDto = pageTemplateIterator.next();
							TemplateDto templateDto = templateService.findTemplateById(pageTemplateDto.getTemplateId());
							xmlWriter.writeCharacters(templateDto.getTemplateText());
						}
					}
				}
				else if (event.isAttribute()){
				//	xmlWriter.writeAttribute(event.getLocation(), event.);
				}

				else if (event.isEndElement()){
					xmlWriter.writeEndElement();
				}
				else if (event.isEndDocument()){
					xmlWriter.writeEndDocument();
				}
			}
		     xmlWriter.flush();
		     xmlWriter.close();

	     } catch (XMLStreamException e) {
			LOGGER.error("Error occured while reading page section from page sections",e);		

	     }

		return new String(StringEscapeUtils.unescapeHtml(out.toString()));
	}

	/**
	 * Sets the template service
	 * @param templateService
	 */
	public void setTemplateService(final TemplateService templateService){
		this.templateService = templateService;
	}

}
