/*
 *  Copyright 2010 Hetatech. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *    1. Redistributions of source code must retain the above copyright notice, this list of
 *       conditions and the following disclaimer.
 *
 *    2. Redistributions in binary form must reproduce the above copyright notice, this list
 *       of conditions and the following disclaimer in the documentation and/or other materials
 *       provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY Hetatech ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL <COPYRIGHT HOLDER> OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of Hetatech.
 */
package org.hetatech.utils;

import java.sql.Connection;

/**
 * Created on Aug 21, 2010
 * @author javajox
 *
 * A set of static methods useful to work with PostgreSQL
 */
public class PGUtils {
	
	//private static Logger log = Logger.getLogger(PGUtils.class.toString());

	/**
	 * 
	 * @param query
	 * @param splitted_query
	 * @param args
	 * @return
	 */
	private static String form_error_string(String query, String[] splitted_query, String[] args) {
		String param_types = "";
		//for(String param_type : splitted_query)
		//	param_types += param_type + "\n";
		String param_values = "";
		for(String param_value : args)
			param_values += param_value + ", ";
		
		return "DETAILS : \n" + "Query : " + query + 
						 //"\n" + "Param Types : " + param_types + 
						 "\n" + "Param Values : " + param_values;
	}
	
	/**
	 * 
	 * @param query SELECT first_name, last_name FROM user WHERE ( nickname = :string) AND ( age > :integer )
	 * @param args[] : 30, john
	 * @return SELECT name, surname, age FROM user WHERE ( age > 30 ) AND ( nickname = 'john' )
	 */
	public static String replace_args(String query, String... args) throws PGUtilsException {
		
		String local_query = query;
		
		String splitted_query[] = local_query.split(":");
		
		if( splitted_query.length == 1 )
			
			throw new PGUtilsException("The query does not contain any of \":data_type\", " + form_error_string(query, splitted_query, args));
		
		int found_datatypes_count = 0;

		// first part contains something like this "SELECT some_thing..."
		for(int i=1; i<splitted_query.length; i++) {
			if( splitted_query[i].startsWith( "string" ) ) {
				local_query = local_query.replaceFirst(":string", "'" + args[i-1] + "'");
				++found_datatypes_count;
			} else 
			if( splitted_query[i].startsWith( "integer" ) ) {
				local_query = local_query.replaceFirst(":integer", Integer.parseInt( args[i-1] ) + "" );
				++found_datatypes_count;
			} else
			if( splitted_query[i].startsWith( "boolean" ) ) {
				local_query = local_query.replaceFirst(":boolean", Boolean.parseBoolean( args[i-1] ) + "" );
				++found_datatypes_count;
			}
		}
		if( ( splitted_query.length - 1 ) != found_datatypes_count ) {
			
			throw new PGUtilsException("The number of datatypes does not match, " + form_error_string(query, splitted_query, args));
		}
		
		return local_query;
	}
	
	/**
	 * 
	 * @param connection The connection to the PostgreSQL data base
	 * @param query The query which must be executed
	 * @param args
	 * @throws Throwable
	 */
	public static void execute_insert(Connection connection, String query, String... args) throws Throwable {
		
		String query2 = replace_args( query, args );
		
		connection.createStatement().execute( query2 );
	}
	
	/**
	 * 
	 * @param connection The connection to the PostgreSQL data base
	 * @param query The query which must be executed
	 * @param args
	 * @throws Throwable
	 */
	public static void execute_delete(Connection connection, String query, String... args) throws Throwable {
		
		String query2 = replace_args( query, args );
		
		connection.createStatement().execute( query2 );
	}
}
