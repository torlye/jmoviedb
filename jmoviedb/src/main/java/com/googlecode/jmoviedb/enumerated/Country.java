/*
 * This file is part of JMoviedb.
 * 
 * Copyright (C) Tor Arne Lye torarnelye@gmail.com
 * 
 * JMoviedb is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 * 
 * JMoviedb is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.googlecode.jmoviedb.enumerated;

import com.googlecode.jmoviedb.*;

@SuppressWarnings("static-access")

public enum Country {
	albania(1, "Albania", Settings.getSettings().getLanguageClass().COUNTRY_ALBANIA, "AL"),
	argentina(2, "Argentina", Settings.getSettings().getLanguageClass().COUNTRY_ARGENTINA, "AR"),
	australia(3, "Australia", Settings.getSettings().getLanguageClass().COUNTRY_AUSTRALIA, "AU"),
	austria(4, "Austria", Settings.getSettings().getLanguageClass().COUNTRY_AUSTRIA, "AT"),
	belgium(5, "Belgium", Settings.getSettings().getLanguageClass().COUNTRY_BELGIUM, "BE"),
	brazil(6, "Brazil", Settings.getSettings().getLanguageClass().COUNTRY_BRAZIL, "BR"),
	bulgaria(7, "Bulgaria", Settings.getSettings().getLanguageClass().COUNTRY_BULGARIA, "BG"),
	canada(8, "Canada", Settings.getSettings().getLanguageClass().COUNTRY_CANADA, "CA"),
	chile(9, "Chile", Settings.getSettings().getLanguageClass().COUNTRY_CHILE, "CL"),
	china(10, "China", Settings.getSettings().getLanguageClass().COUNTRY_CHINA, "CN"),
	colombia(11, "Colombia", Settings.getSettings().getLanguageClass().COUNTRY_COLOMBIA, "CO"),
	croatia(12, "Croatia", Settings.getSettings().getLanguageClass().COUNTRY_CROATIA, "HR"),
	cuba(13, "Cuba", Settings.getSettings().getLanguageClass().COUNTRY_CUBA, "CU"),
	czechrepublic(14, "CzechRepublic", Settings.getSettings().getLanguageClass().COUNTRY_CZECHREPUBLIC, "CZ"),
	czechoslovakia(15, "Czechoslovakia", Settings.getSettings().getLanguageClass().COUNTRY_CZECHOSLOVAKIA, "XC"),
	denmark(16, "Denmark", Settings.getSettings().getLanguageClass().COUNTRY_DENMARK, "DK"),
	eastgermany(17, "EastGermany", Settings.getSettings().getLanguageClass().COUNTRY_EASTGERMANY, "XG"),
	egypt(18, "Egypt", Settings.getSettings().getLanguageClass().COUNTRY_EGYPT, "EG"),
	finland(19, "Finland", Settings.getSettings().getLanguageClass().COUNTRY_FINLAND, "FI"),
	france(20, "France", Settings.getSettings().getLanguageClass().COUNTRY_FRANCE, "FR"),
	germany(21, "Germany", Settings.getSettings().getLanguageClass().COUNTRY_GERMANY, "DE"),
	greece(22, "Greece", Settings.getSettings().getLanguageClass().COUNTRY_GREECE, "GR"),
	hongkong(23, "HongKong", Settings.getSettings().getLanguageClass().COUNTRY_HONGKONG, "HK"),
	hungary(24, "Hungary", Settings.getSettings().getLanguageClass().COUNTRY_HUNGARY, "HU"),
	india(25, "India", Settings.getSettings().getLanguageClass().COUNTRY_INDIA, "IN"),
	indonesia(26, "Indonesia", Settings.getSettings().getLanguageClass().COUNTRY_INDONESIA, "ID"),
	iran(27, "Iran", Settings.getSettings().getLanguageClass().COUNTRY_IRAN, "IR"),
	ireland(28, "Ireland", Settings.getSettings().getLanguageClass().COUNTRY_IRELAND, "IE"),
	israel(29, "Israel", Settings.getSettings().getLanguageClass().COUNTRY_ISRAEL, "IL"),
	italy(30, "Italy", Settings.getSettings().getLanguageClass().COUNTRY_ITALY, "IT"),
	japan(31, "Japan", Settings.getSettings().getLanguageClass().COUNTRY_JAPAN, "JP"),
	mexico(32, "Mexico", Settings.getSettings().getLanguageClass().COUNTRY_MEXICO, "MX"),
	netherlands(33, "Netherlands", Settings.getSettings().getLanguageClass().COUNTRY_NETHERLANDS, "NL"),
	newzealand(34, "NewZealand", Settings.getSettings().getLanguageClass().COUNTRY_NEWZEALAND, "NZ"),
	nigeria(35, "Nigeria", Settings.getSettings().getLanguageClass().COUNTRY_NIGERIA, "NG"),
	norway(36, "Norway", Settings.getSettings().getLanguageClass().COUNTRY_NORWAY, "NO"),
	philippines(37, "Philippines", Settings.getSettings().getLanguageClass().COUNTRY_PHILIPPINES, "PH"),
	poland(38, "Poland", Settings.getSettings().getLanguageClass().COUNTRY_POLAND, "PL"),
	portugal(39, "Portugal", Settings.getSettings().getLanguageClass().COUNTRY_PORTUGAL, "PT"),
	romania(40, "Romania", Settings.getSettings().getLanguageClass().COUNTRY_ROMANIA, "RO"),
	russia(41, "Russia", Settings.getSettings().getLanguageClass().COUNTRY_RUSSIA, "RU"),
	southafrica(42, "SouthAfrica", Settings.getSettings().getLanguageClass().COUNTRY_SOUTHAFRICA, "ZA"),
	southkorea(43, "SouthKorea", Settings.getSettings().getLanguageClass().COUNTRY_SOUTHKOREA, "KR"),
	sovietunion(44, "SovietUnion", Settings.getSettings().getLanguageClass().COUNTRY_SOVIETUNION, "SU"),
	spain(45, "Spain", Settings.getSettings().getLanguageClass().COUNTRY_SPAIN, "ES"),
	sweden(46, "Sweden", Settings.getSettings().getLanguageClass().COUNTRY_SWEDEN, "SE"),
	switzerland(47, "Switzerland", Settings.getSettings().getLanguageClass().COUNTRY_SWITZERLAND, "CH"),
	taiwan(48, "Taiwan", Settings.getSettings().getLanguageClass().COUNTRY_TAIWAN, "TW"),
	turkey(49, "Turkey", Settings.getSettings().getLanguageClass().COUNTRY_TURKEY, "TR"),
	uk(50, "UK", Settings.getSettings().getLanguageClass().COUNTRY_UK, "GB"),
	usa(51, "USA", Settings.getSettings().getLanguageClass().COUNTRY_USA, "US"),
	venezuela(52, "Venezuela", Settings.getSettings().getLanguageClass().COUNTRY_VENEZUELA, "VE"),
	westgermany(53, "WestGermany", Settings.getSettings().getLanguageClass().COUNTRY_WESTGERMANY, "DE"),
	yugoslavia(54, "Yugoslavia", Settings.getSettings().getLanguageClass().COUNTRY_YUGOSLAVIA, "YU"),
	afghanistan(55, "Afghanistan", Settings.getSettings().getLanguageClass().COUNTRY_AFGHANISTAN, "AF"),
	algeria(56, "Algeria", Settings.getSettings().getLanguageClass().COUNTRY_ALGERIA, "DZ"),
	andorra(57, "Andorra", Settings.getSettings().getLanguageClass().COUNTRY_ANDORRA, "AD"),
	angola(58, "Angola", Settings.getSettings().getLanguageClass().COUNTRY_ANGOLA, "AO"),
	antiguaandbarbuda(59, "AntiguaandBarbuda", Settings.getSettings().getLanguageClass().COUNTRY_ANTIGUAANDBARBUDA, "AG"),
	armenia(60, "Armenia", Settings.getSettings().getLanguageClass().COUNTRY_ARMENIA, "AM"),
	aruba(61, "Aruba", Settings.getSettings().getLanguageClass().COUNTRY_ARUBA, "AW"),
	azerbaijan(62, "Azerbaijan", Settings.getSettings().getLanguageClass().COUNTRY_AZERBAIJAN, "AZ"),
	bahamas(63, "Bahamas", Settings.getSettings().getLanguageClass().COUNTRY_BAHAMAS, "BS"),
	bahrain(64, "Bahrain", Settings.getSettings().getLanguageClass().COUNTRY_BAHRAIN, "BH"),
	bangladesh(65, "Bangladesh", Settings.getSettings().getLanguageClass().COUNTRY_BANGLADESH, "BD"),
	barbados(66, "Barbados", Settings.getSettings().getLanguageClass().COUNTRY_BARBADOS, "BB"),
	belarus(67, "Belarus", Settings.getSettings().getLanguageClass().COUNTRY_BELARUS, "BY"),
	belize(68, "Belize", Settings.getSettings().getLanguageClass().COUNTRY_BELIZE, "BZ"),
	benin(69, "Benin", Settings.getSettings().getLanguageClass().COUNTRY_BENIN, "BJ"),
	bhutan(70, "Bhutan", Settings.getSettings().getLanguageClass().COUNTRY_BHUTAN, "BT"),
	bolivia(71, "Bolivia", Settings.getSettings().getLanguageClass().COUNTRY_BOLIVIA, "BO"),
	bosnia_herzegovina(72, "Bosnia-Herzegovina", Settings.getSettings().getLanguageClass().COUNTRY_BOSNIA_HERZEGOVINA, "BA"),
	botswana(73, "Botswana", Settings.getSettings().getLanguageClass().COUNTRY_BOTSWANA, "BW"),
	burkinafaso(74, "BurkinaFaso", Settings.getSettings().getLanguageClass().COUNTRY_BURKINAFASO, "BF"),
	burma(75, "Burma", Settings.getSettings().getLanguageClass().COUNTRY_BURMA, "MM"),
	burundi(76, "Burundi", Settings.getSettings().getLanguageClass().COUNTRY_BURUNDI, "BI"),
	cambodia(77, "Cambodia", Settings.getSettings().getLanguageClass().COUNTRY_CAMBODIA, "KH"),
	cameroon(78, "Cameroon", Settings.getSettings().getLanguageClass().COUNTRY_CAMEROON, "CM"),
	capeverde(79, "CapeVerde", Settings.getSettings().getLanguageClass().COUNTRY_CAPEVERDE, "CV"),
	centralafricanrepublic(80, "CentralAfricanRepublic", Settings.getSettings().getLanguageClass().COUNTRY_CENTRALAFRICANREPUBLIC, "CF"),
	chad(81, "Chad", Settings.getSettings().getLanguageClass().COUNTRY_CHAD, "TD"),
	congo(82, "Congo", Settings.getSettings().getLanguageClass().COUNTRY_CONGO, "CG"),
	costarica(83, "CostaRica", Settings.getSettings().getLanguageClass().COUNTRY_COSTARICA, "CR"),
	cyprus(84, "Cyprus", Settings.getSettings().getLanguageClass().COUNTRY_CYPRUS, "CY"),
	democraticrepublicofcongo(85, "DemocraticRepublicofCongo", Settings.getSettings().getLanguageClass().COUNTRY_DEMOCRATICREPUBLICOFCONGO, "CD"),
	djibouti(86, "Djibouti", Settings.getSettings().getLanguageClass().COUNTRY_DJIBOUTI, "DJ"),
	dominicanrepublic(87, "DominicanRepublic", Settings.getSettings().getLanguageClass().COUNTRY_DOMINICANREPUBLIC, "DO"),
	ecuador(88, "Ecuador", Settings.getSettings().getLanguageClass().COUNTRY_ECUADOR, "EC"),
	elsalvador(89, "ElSalvador", Settings.getSettings().getLanguageClass().COUNTRY_ELSALVADOR, "SV"),
	eritrea(90, "Eritrea", Settings.getSettings().getLanguageClass().COUNTRY_ERITREA, "ER"),
	estonia(91, "Estonia", Settings.getSettings().getLanguageClass().COUNTRY_ESTONIA, "EE"),
	ethiopia(92, "Ethiopia", Settings.getSettings().getLanguageClass().COUNTRY_ETHIOPIA, "ET"),
	faroeislands(93, "FaroeIslands", Settings.getSettings().getLanguageClass().COUNTRY_FAROEISLANDS, "FO"),
	federalrepublicofyugoslavia(94, "FederalRepublicofYugoslavia", Settings.getSettings().getLanguageClass().COUNTRY_FEDERALREPUBLICOFYUGOSLAVIA, "YU"),
	fiji(95, "Fiji", Settings.getSettings().getLanguageClass().COUNTRY_FIJI, "FJ"),
	gabon(96, "Gabon", Settings.getSettings().getLanguageClass().COUNTRY_GABON, "GA"),
	georgia(97, "Georgia", Settings.getSettings().getLanguageClass().COUNTRY_GEORGIA, "GE"),
	ghana(98, "Ghana", Settings.getSettings().getLanguageClass().COUNTRY_GHANA, "GH"),
	greenland(99, "Greenland", Settings.getSettings().getLanguageClass().COUNTRY_GREENLAND, "GL"),
	guadeloupe(100, "Guadeloupe", Settings.getSettings().getLanguageClass().COUNTRY_GUADELOUPE, "GP"),
	guatemala(101, "Guatemala", Settings.getSettings().getLanguageClass().COUNTRY_GUATEMALA, "GT"),
	guinea(102, "Guinea", Settings.getSettings().getLanguageClass().COUNTRY_GUINEA, "GN"),
	guinea_bissau(103, "Guinea-Bissau", Settings.getSettings().getLanguageClass().COUNTRY_GUINEA_BISSAU, "GW"),
	guyana(104, "Guyana", Settings.getSettings().getLanguageClass().COUNTRY_GUYANA, "GY"),
	haiti(105, "Haiti", Settings.getSettings().getLanguageClass().COUNTRY_HAITI, "HT"),
	honduras(106, "Honduras", Settings.getSettings().getLanguageClass().COUNTRY_HONDURAS, "HN"),
	iceland(107, "Iceland", Settings.getSettings().getLanguageClass().COUNTRY_ICELAND, "IS"),
	iraq(108, "Iraq", Settings.getSettings().getLanguageClass().COUNTRY_IRAQ, "IQ"),
	ivorycoast(109, "IvoryCoast", Settings.getSettings().getLanguageClass().COUNTRY_IVORYCOAST, "CI"),
	jamaica(110, "Jamaica", Settings.getSettings().getLanguageClass().COUNTRY_JAMAICA, "JM"),
	jordan(111, "Jordan", Settings.getSettings().getLanguageClass().COUNTRY_JORDAN, "JO"),
	kazakhstan(112, "Kazakhstan", Settings.getSettings().getLanguageClass().COUNTRY_KAZAKHSTAN, "KZ"),
	kenya(113, "Kenya", Settings.getSettings().getLanguageClass().COUNTRY_KENYA, "KE"),
	korea(114, "Korea", Settings.getSettings().getLanguageClass().COUNTRY_KOREA),
	kosovo(115, "Kosovo", Settings.getSettings().getLanguageClass().COUNTRY_KOSOVO, "XK"),
	kuwait(116, "Kuwait", Settings.getSettings().getLanguageClass().COUNTRY_KUWAIT, "KW"),
	kyrgyzstan(117, "Kyrgyzstan", Settings.getSettings().getLanguageClass().COUNTRY_KYRGYZSTAN, "KG"),
	laos(118, "Laos", Settings.getSettings().getLanguageClass().COUNTRY_LAOS, "LA"),
	latvia(119, "Latvia", Settings.getSettings().getLanguageClass().COUNTRY_LATVIA, "LV"),
	lebanon(120, "Lebanon", Settings.getSettings().getLanguageClass().COUNTRY_LEBANON, "LB"),
	lesotho(121, "Lesotho", Settings.getSettings().getLanguageClass().COUNTRY_LESOTHO, "LS"),
	liberia(122, "Liberia", Settings.getSettings().getLanguageClass().COUNTRY_LIBERIA, "LR"),
	libya(123, "Libya", Settings.getSettings().getLanguageClass().COUNTRY_LIBYA, "LY"),
	liechtenstein(124, "Liechtenstein", Settings.getSettings().getLanguageClass().COUNTRY_LIECHTENSTEIN, "LI"),
	lithuania(125, "Lithuania", Settings.getSettings().getLanguageClass().COUNTRY_LITHUANIA, "LT"),
	luxembourg(126, "Luxembourg", Settings.getSettings().getLanguageClass().COUNTRY_LUXEMBOURG, "LU"),
	macau(127, "Macau", Settings.getSettings().getLanguageClass().COUNTRY_MACAU, "MO"),
	madagascar(128, "Madagascar", Settings.getSettings().getLanguageClass().COUNTRY_MADAGASCAR, "MG"),
	malaysia(129, "Malaysia", Settings.getSettings().getLanguageClass().COUNTRY_MALAYSIA, "MY"),
	mali(130, "Mali", Settings.getSettings().getLanguageClass().COUNTRY_MALI, "ML"),
	malta(131, "Malta", Settings.getSettings().getLanguageClass().COUNTRY_MALTA, "MT"),
	martinique(132, "Martinique", Settings.getSettings().getLanguageClass().COUNTRY_MARTINIQUE, "MQ"),
	mauritania(133, "Mauritania", Settings.getSettings().getLanguageClass().COUNTRY_MAURITANIA, "MR"),
	mauritius(134, "Mauritius", Settings.getSettings().getLanguageClass().COUNTRY_MAURITIUS, "MU"),
	moldova(135, "Moldova", Settings.getSettings().getLanguageClass().COUNTRY_MOLDOVA, "MD"),
	monaco(136, "Monaco", Settings.getSettings().getLanguageClass().COUNTRY_MONACO, "MC"),
	mongolia(137, "Mongolia", Settings.getSettings().getLanguageClass().COUNTRY_MONGOLIA, "MN"),
	morocco(138, "Morocco", Settings.getSettings().getLanguageClass().COUNTRY_MOROCCO, "MA"),
	mozambique(139, "Mozambique", Settings.getSettings().getLanguageClass().COUNTRY_MOZAMBIQUE, "MZ"),
	namibia(140, "Namibia", Settings.getSettings().getLanguageClass().COUNTRY_NAMIBIA, "NA"),
	nepal(141, "Nepal", Settings.getSettings().getLanguageClass().COUNTRY_NEPAL, "NP"),
	nicaragua(142, "Nicaragua", Settings.getSettings().getLanguageClass().COUNTRY_NICARAGUA, "NI"),
	niger(143, "Niger", Settings.getSettings().getLanguageClass().COUNTRY_NIGER, "NE"),
	niue(144, "Niue", Settings.getSettings().getLanguageClass().COUNTRY_NIUE, "NU"),
	northkorea(145, "NorthKorea", Settings.getSettings().getLanguageClass().COUNTRY_NORTHKOREA, "KP"),
	northvietnam(146, "NorthVietnam", Settings.getSettings().getLanguageClass().COUNTRY_NORTHVIETNAM),
	oman(147, "Oman", Settings.getSettings().getLanguageClass().COUNTRY_OMAN, "OM"),
	pakistan(148, "Pakistan", Settings.getSettings().getLanguageClass().COUNTRY_PAKISTAN, "PK"),
	palestine(149, "Palestine", Settings.getSettings().getLanguageClass().COUNTRY_PALESTINE, "PS"),
	panama(150, "Panama", Settings.getSettings().getLanguageClass().COUNTRY_PANAMA, "PA"),
	papuanewguinea(151, "PapuaNewGuinea", Settings.getSettings().getLanguageClass().COUNTRY_PAPUANEWGUINEA, "PG"),
	paraguay(152, "Paraguay", Settings.getSettings().getLanguageClass().COUNTRY_PARAGUAY, "PY"),
	peru(153, "Peru", Settings.getSettings().getLanguageClass().COUNTRY_PERU, "PE"),
	puertorico(154, "PuertoRico", Settings.getSettings().getLanguageClass().COUNTRY_PUERTORICO, "PR"),
	qatar(155, "Qatar", Settings.getSettings().getLanguageClass().COUNTRY_QATAR, "QA"),
	republicofmacedonia(156, "RepublicofMacedonia", Settings.getSettings().getLanguageClass().COUNTRY_REPUBLICOFMACEDONIA, "MK"),
	rwanda(157, "Rwanda", Settings.getSettings().getLanguageClass().COUNTRY_RWANDA, "RW"),
	sanmarino(158, "SanMarino", Settings.getSettings().getLanguageClass().COUNTRY_SANMARINO, "SM"),
	saudiarabia(159, "SaudiArabia", Settings.getSettings().getLanguageClass().COUNTRY_SAUDIARABIA, "SA"),
	senegal(160, "Senegal", Settings.getSettings().getLanguageClass().COUNTRY_SENEGAL, "SN"),
	serbia(161, "Serbia", Settings.getSettings().getLanguageClass().COUNTRY_SERBIA, "RS"),
	serbiaandmontenegro(162, "SerbiaandMontenegro", Settings.getSettings().getLanguageClass().COUNTRY_SERBIAANDMONTENEGRO, "CS"),
	seychelles(163, "Seychelles", Settings.getSettings().getLanguageClass().COUNTRY_SEYCHELLES, "SC"),
	siam(164, "Siam", Settings.getSettings().getLanguageClass().COUNTRY_SIAM),
	singapore(165, "Singapore", Settings.getSettings().getLanguageClass().COUNTRY_SINGAPORE, "SG"),
	slovakia(166, "Slovakia", Settings.getSettings().getLanguageClass().COUNTRY_SLOVAKIA, "SK"),
	slovenia(167, "Slovenia", Settings.getSettings().getLanguageClass().COUNTRY_SLOVENIA, "SI"),
	somalia(168, "Somalia", Settings.getSettings().getLanguageClass().COUNTRY_SOMALIA, "SO"),
	srilanka(169, "SriLanka", Settings.getSettings().getLanguageClass().COUNTRY_SRILANKA, "LK"),
	sudan(170, "Sudan", Settings.getSettings().getLanguageClass().COUNTRY_SUDAN, "SD"),
	suriname(171, "Suriname", Settings.getSettings().getLanguageClass().COUNTRY_SURINAME, "SR"),
	syria(172, "Syria", Settings.getSettings().getLanguageClass().COUNTRY_SYRIA, "SY"),
	tajikistan(173, "Tajikistan", Settings.getSettings().getLanguageClass().COUNTRY_TAJIKISTAN, "TJ"),
	tanzania(174, "Tanzania", Settings.getSettings().getLanguageClass().COUNTRY_TANZANIA, "TZ"),
	thailand(175, "Thailand", Settings.getSettings().getLanguageClass().COUNTRY_THAILAND, "TH"),
	togo(176, "Togo", Settings.getSettings().getLanguageClass().COUNTRY_TOGO, "TG"),
	tonga(177, "Tonga", Settings.getSettings().getLanguageClass().COUNTRY_TONGA, "TO"),
	trinidadandtobago(178, "TrinidadAndTobago", Settings.getSettings().getLanguageClass().COUNTRY_TRINIDADANDTOBAGO, "TT"),
	tunisia(179, "Tunisia", Settings.getSettings().getLanguageClass().COUNTRY_TUNISIA, "TN"),
	turkmenistan(180, "Turkmenistan", Settings.getSettings().getLanguageClass().COUNTRY_TURKMENISTAN, "TM"),
	uganda(181, "Uganda", Settings.getSettings().getLanguageClass().COUNTRY_UGANDA, "UG"),
	ukraine(182, "Ukraine", Settings.getSettings().getLanguageClass().COUNTRY_UKRAINE, "UA"),
	unitedarabemirates(183, "UnitedArabEmirates", Settings.getSettings().getLanguageClass().COUNTRY_UNITEDARABEMIRATES, "AE"),
	uruguay(184, "Uruguay", Settings.getSettings().getLanguageClass().COUNTRY_URUGUAY, "UY"),
	uzbekistan(185, "Uzbekistan", Settings.getSettings().getLanguageClass().COUNTRY_UZBEKISTAN, "UZ"),
	vietnam(186, "Vietnam", Settings.getSettings().getLanguageClass().COUNTRY_VIETNAM, "VN"),
	westernsahara(187, "WesternSahara", Settings.getSettings().getLanguageClass().COUNTRY_WESTERNSAHARA, "EH"),
	yemen(188, "Yemen", Settings.getSettings().getLanguageClass().COUNTRY_YEMEN, "YE"),
	zaire(189, "Zaire", Settings.getSettings().getLanguageClass().COUNTRY_ZAIRE, "ZR"),
	zambia(190, "Zambia", Settings.getSettings().getLanguageClass().COUNTRY_ZAMBIA, "ZM"),
	zimbabwe(191, "Zimbabwe", Settings.getSettings().getLanguageClass().COUNTRY_ZIMBABWE, "ZW");

	
	private int id;
	private String imdbID;
	private String name;
	private String iso3166;
	
	/**
	 * Constructor.
	 * @param id a numerical ID, used for storage in the SQL database
	 * @param imdbID IMDb's ID for this language
	 * @param name the name of this language
	 */
	Country(int id, String imdbID, String name) {
		this.id = id;
		this.imdbID = imdbID;
		this.name = name;
	}

	Country(int id, String imdbID, String name, String iso3166) {
		this(id, imdbID, name);
		this.iso3166 = iso3166;
	}
	
	public String getImdbID() {
		return imdbID;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIso3166_1_alpha_2() {
		return iso3166;
	}
	
	/**
	 * Converts a string to a Country enum, for example &quot;Finland&quot; to Country.finland 
	 * @param string the string to be converted
	 * @return a Country enum, or null if there was no match
	 */
	public static Country StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(Country c : Country.values())
			if(string.toLowerCase().equals(c.getImdbID().toLowerCase()))
				return c;
		return null;
	}
	
	public static Country StringToEnumUsingName(String string) {
		if(string == null) {
			return null;
		}
		for(Country c : Country.values())
			if(string.toLowerCase().equals(c.getName().toLowerCase()))
				return c;
		return null;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static Country intToEnum(int id) {
		for(Country c : Country.values())
			if(id == c.getID())
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised country ID: " + id);
		return null;
	}
	
	public static Country iso3166ToEnum(String isoCode) {
		for(Country c : Country.values())
			if(c.iso3166 != null && isoCode.equals(c.iso3166))
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised ISO country code: " + isoCode);
		return null;
	}
	
	public static Country nameToEnum(String countryName) {
		for(Country c : Country.values())
			if(countryName.equals(c.name))
				return c;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised ISO country code: " + countryName);
		return null;
	}
	
	public static Country tmdbCountryToEnum(info.movito.themoviedbapi.model.ProductionCountry tmdbCountry) {
		Country country = iso3166ToEnum(tmdbCountry.getIsoCode());
		if (country != null) return country;

		country = nameToEnum(tmdbCountry.getName());
		if (country != null) return country;

		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised ISO country code: " + tmdbCountry);
		return null;
	}
}
