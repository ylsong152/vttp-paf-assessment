package vttp2023.batch4.paf.assessment.repositories;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.json.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationOperation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SortOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import vttp2023.batch4.paf.assessment.Utils;
import vttp2023.batch4.paf.assessment.models.Accommodation;
import vttp2023.batch4.paf.assessment.models.AccommodationSummary;

@Repository
public class ListingsRepository {
	
	// You may add additional dependency injections

	@Autowired
	private MongoTemplate template;

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 *
	 * 
	// Aggregate
	db.listings.aggregate([
		{ $match: {"address.suburb": { "$nin": [ null, "" ]}}},
		{ $group: {_id: "$_id", suburbs: {$push: "$address.suburb"}}},
		{ $unwind: "$suburbs"},
		{ $sort: {suburbs: 1}}
	])

	// Distinct
	db.listings.distinct("address.suburb", { "address.suburb" : { $nin : ["", null] } })
	 *
	 */
	public List<String> getSuburbs(String country) {
		List<String> suburbs = template.getCollection("listings").distinct("address.suburb", String.class).into(new ArrayList<>());
		return suburbs;
	}

	/*
	 * Write the native MongoDB query that you will be using for this method
	 * inside this comment block
	 * eg. db.bffs.find({ name: 'fred }) 
	 *
	db.listings.find(
	{
		"address.suburb": "Zetland",
		price: {$lte: 1000},
		accommodates: {$gte: 2},
		min_nights: {$lte: 16}
	},
	{
		_id: 1,
		name: 1,
		accommodates: 1,
		price: 1,
	}
	).sort({price: -1})
	 *
	 */
	public List<AccommodationSummary> findListings(String suburb, int persons, int duration, float priceRange) {
		Criteria criteria = Criteria.where("address.suburb").is(suburb)
									.and("price").lte(priceRange)
									.and("accommodates").gte(persons)
									.and("min_nights").lte(duration);
		Query query = new Query(criteria);
		query.fields().include("_id").include("name").include("accommodates").include("price");
		BsonDocument doc = new BsonDocument();

		List<AccommodationSummary> accommsList = template.find(query, AccommodationSummary.class);

		return accommsList;
	}

	// IMPORTANT: DO NOT MODIFY THIS METHOD UNLESS REQUESTED TO DO SO
	// If this method is changed, any assessment task relying on this method will
	// not be marked
	public Optional<Accommodation> findAccommodatationById(String id) {
		Criteria criteria = Criteria.where("_id").is(id);
		Query query = Query.query(criteria);

		List<Document> result = template.find(query, Document.class, "listings");
		if (result.size() <= 0)
			return Optional.empty();

		return Optional.of(Utils.toAccommodation(result.getFirst()));
	}

}
