package com.restaurant.orderservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import com.restaurant.orderservice.entity.Sequence;

@Service
public class SequenceGenerator {

	@Autowired
	private MongoOperations mongoOperations;
	
	public int generateNextOrderId() {
				System.out.println("Before NextOrderId");
		Sequence counter = mongoOperations.findAndModify(
				Query.query(Criteria.where("_id").is("sequence")), // Find document by _id = "sequence"
			    new Update().inc("sequence", 1), // Increment "sequence" by 1
			    FindAndModifyOptions.options().returnNew(true).upsert(true), // Options to return the new document and upsert if not found
			    Sequence.class); // The class for the MongoDB document)
		
		System.out.println("counter.getSequence()"+counter.getSequence()+","+counter.getId());
		return counter.getSequence();
	}
}
