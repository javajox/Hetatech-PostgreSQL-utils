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
package test.org.hetatech.utils;

import org.hetatech.utils.PGUtils;

/**
 * Created on Aug 21, 2010
 * @author javajox
 *
 */
public class Testing {


	public static void main(String[] args) {

		try {
		  // test 1, should be ok
		  System.out.println("Query 1\n");
		  String test_query = "SELECT first_name, last_name FROM user WHERE ( nickname = :string ) AND ( age > :integer )";
		
		  String cooked_query = PGUtils.replace_args(test_query, "John", "30");

		  System.out.println("cooked query = " + cooked_query);
		}catch(Throwable cause) {
			
			cause.printStackTrace();
		}
		
		
		try {
		  // test 2, should fail
		  System.out.println("Query 2\n");
		  String test_query2 = "SELECT first_name, last_name FROM user WHERE ( nickname = :zzzz ) AND ( age > :integer )";
		
		  String cooked_query2 = PGUtils.replace_args(test_query2, "John", "30");
		
		  System.out.println("cooked query 2 = " + cooked_query2);
		}catch(Throwable cause) {
			
			cause.printStackTrace();
		}
		
		try {
			// should fail
			System.out.println("Query 3\n");
			String test_query3 = "SELECT first_name, last_name FROM user WHERE (nickname = 'John') AND ( age > 21 )";
			
			String cooked_query3 = PGUtils.replace_args(test_query3, "John", "30");
			
			System.out.println("cooked query 3 = " + cooked_query3);
		}catch(Throwable cause) {
			
			cause.printStackTrace();
		}
		
	}

}
