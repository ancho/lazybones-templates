package de.ancho

import spock.lang.Specification

class PersonSpec extends Specification {

	def 'shouldFail'(){
		expect:
			false == true
	}
}
