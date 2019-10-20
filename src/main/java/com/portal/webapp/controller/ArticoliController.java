package com.portal.webapp.controller;

import java.util.List;

import javax.validation.Valid;

import com.portal.webapp.entity.Article;
import com.portal.webapp.entity.Barcode;
import com.portal.webapp.exception.BindingException;
import com.portal.webapp.exception.NotFoundException;
import com.portal.webapp.service.ArticleService;
import com.portal.webapp.service.BarcodeService;
import com.portal.webapp.exception.DuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("api/article")
//@CrossOrigin(origins="http://localhost:4200")
public class ArticoliController 
{
	private static final Logger logger = LoggerFactory.getLogger(ArticoliController.class);
	
	@Autowired
	private ArticleService articoliService;
	
	@Autowired
	private BarcodeService barcodeService;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	@GetMapping(value = "/test")
	public ResponseEntity<?> testConnection() {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Test connection ok");
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
	}
	
	// ------------------- Search for Barcode ------------------------------------
	@GetMapping(value = "/search/ean/{barcode}", produces = "application/json")
	public ResponseEntity<Article> getArticleByEan(@PathVariable("barcode") String barcode) throws NotFoundException {
		logger.info("****** Getting article with barcode " + barcode + " *******");
		
		Article article;
		Barcode ean = barcodeService.getByBarcode(barcode);
		
		if (ean == null) {
			String errMsg = String.format("Barcode %s not found", barcode);
			logger.warn(errMsg);
			throw new NotFoundException(errMsg);
		} else {
			article = ean.getArticolo();
		}
		
		return new ResponseEntity<Article>(article, HttpStatus.OK);
		
	}
	
	// ------------------- Search by code ------------------------------------
	@GetMapping(value = "/search/code/{codart}", produces = "application/json")
	public ResponseEntity<Article> listArtByCodArt(@PathVariable("codart") String codArt) throws NotFoundException {
		logger.info("****** Getting item with code " + codArt + " *******");
		
		Article article = articoliService.getByCodArt(codArt);
		
		if (article == null) {

			String ErrMsg = String.format("L'articolo con codice %s non è stato trovato!", codArt);
			logger.warn(ErrMsg);
			throw new NotFoundException(ErrMsg);
		}
		
		return new ResponseEntity<Article>(article, HttpStatus.OK);
	}
	
	// ------------------- Search by description ------------------------------------
	@GetMapping(value = "/search/description/{filter}", produces = "application/json")
	public ResponseEntity<List<Article>> getArtByDescription(@PathVariable("filter") String filter) throws NotFoundException {

		logger.info("****** Getting article with description: " + filter + " *******");
		List<Article> article = articoliService.getByDescription(filter.toUpperCase() + "%");
		
		if (article == null) {
			String errMsg = String.format("No article found with description %s", filter);
			logger.warn(errMsg);
			
			throw new NotFoundException(errMsg);
		}
		
		return new ResponseEntity<List<Article>>(article, HttpStatus.OK);
	}
	
	// ------------------- Insert item ------------------------------------
	@PostMapping(value = "/insert")
	public ResponseEntity<?> createArticle(@Valid @RequestBody Article article, BindingResult bindingResult) throws BindingException, DuplicateException {
		logger.info("Salviamo l'articolo con codice " + article.getCodArt());
		
		//Check if item has no error
		if (bindingResult.hasErrors()) {
			String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			logger.warn(MsgErr);
			throw new BindingException(MsgErr);
		}

		Article checkArt =  articoliService.getByCodArt(article.getCodArt());
		
		if (checkArt != null) {
			String MsgErr = String.format("Item %s already existing in catalogue ", article.getCodArt());
			logger.warn(MsgErr);
			throw new DuplicateException(MsgErr);
		}
		
		articoliService.insertArticle(article);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.CREATED.toString());
		responseNode.put("message", "Item " + article.getCodArt() + "inserted successfully");
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	// ------------------- UPDATE ITEM ------------------------------------
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public ResponseEntity<?> updateArt(@Valid @RequestBody Article article, BindingResult bindingResult) throws BindingException,NotFoundException {

		logger.info("Update item with codice " + article.getCodArt());
		
		if (bindingResult.hasErrors()) {
			String msgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
			logger.warn(msgErr);
			throw new BindingException(msgErr);
		}
		
		Article checkArt =  articoliService.getByCodArt(article.getCodArt());

		if (checkArt == null) {
			String msgErr = String.format("Item %s not found", article.getCodArt());
			logger.warn(msgErr);
			throw new NotFoundException(msgErr);
		}
		
		articoliService.insertArticle(article);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.CREATED.toString());
		responseNode.put("message", "Item " + article.getCodArt() + " updated successfully");
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.CREATED);
	}
	
	// ------------------- DELETE ITEM ------------------------------------
	@RequestMapping(value = "/delete/{codart}", method = RequestMethod.DELETE, produces = "application/json" )
	public ResponseEntity<?> deleteArt(@PathVariable("codart") String codArt) throws NotFoundException {

		logger.info("Deleting item with code " + codArt);
		
		Article article = articoliService.getByCodArt(codArt);
		
		if (article == null) {

			String msgErr = String.format("Item %s non presente in anagrafica! ",codArt);
			logger.warn(msgErr);
			
			throw new NotFoundException(msgErr);
		}
		
		articoliService.deleteArticle(article);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Item " + codArt + " deleted successfully");
		
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
				
	}

}
