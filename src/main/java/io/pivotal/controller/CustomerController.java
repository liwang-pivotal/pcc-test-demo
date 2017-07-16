package io.pivotal.controller;

import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.query.CqAttributes;
import org.apache.geode.cache.query.CqAttributesFactory;
import org.apache.geode.cache.query.CqQuery;
import org.apache.geode.cache.query.QueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.codearte.jfairy.Fairy;
import io.codearte.jfairy.producer.person.Person;
import io.pivotal.SimpleCQListener;
import io.pivotal.domain.Customer;
import io.pivotal.repo.CustomerRepository;

import java.util.Iterator;

@RestController
public class CustomerController {
	
	@Autowired
	private CustomerRepository repository;
	
	@Autowired
	private ClientCache clientCache;
	
	Fairy fairy = Fairy.create();
	
	@RequestMapping(method = RequestMethod.GET, path = "/create")
	@ResponseBody
	public String create() throws Exception {

		Person person = fairy.person();
		Customer customer = new Customer(person.passportNumber(), person.firstName(), person.lastName());
		repository.save(customer);

		return "New Customer Info Created! <Customer>: " + customer;
	}

	@RequestMapping(method = RequestMethod.GET, path = "/show")
	@ResponseBody
	public String show() throws Exception {
		String resultString = "";
		
		Iterator<?> iter = repository.findAll().iterator();		
		while (iter.hasNext()) {
			resultString += iter.next().toString() + "<br/>";
		}

		return resultString;
	}
	
	@RequestMapping(method = RequestMethod.GET, path = "/cq")
	@ResponseBody
	public String cq() throws Exception {
		QueryService queryService = clientCache.getQueryService();
		
		CqAttributesFactory cqAf = new CqAttributesFactory();
		cqAf.addCqListener(new SimpleCQListener());
		CqAttributes cqa = cqAf.create();
		
		String query = "SELECT * FROM /customer";
		
		CqQuery myCq = queryService.newCq("myCQ", query, cqa);
		
		myCq.execute();

		return "CQ registeration success!";
	}
}
