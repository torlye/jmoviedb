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
	albania(1, "Albania", Settings.getSettings().getLanguageClass().COUNTRY_ALBANIA),
	argentina(2, "Argentina", Settings.getSettings().getLanguageClass().COUNTRY_ARGENTINA),
	australia(3, "Australia", Settings.getSettings().getLanguageClass().COUNTRY_AUSTRALIA),
	austria(4, "Austria", Settings.getSettings().getLanguageClass().COUNTRY_AUSTRIA),
	belgium(5, "Belgium", Settings.getSettings().getLanguageClass().COUNTRY_BELGIUM),
	brazil(6, "Brazil", Settings.getSettings().getLanguageClass().COUNTRY_BRAZIL),
	bulgaria(7, "Bulgaria", Settings.getSettings().getLanguageClass().COUNTRY_BULGARIA),
	canada(8, "Canada", Settings.getSettings().getLanguageClass().COUNTRY_CANADA),
	chile(9, "Chile", Settings.getSettings().getLanguageClass().COUNTRY_CHILE),
	china(10, "China", Settings.getSettings().getLanguageClass().COUNTRY_CHINA),
	colombia(11, "Colombia", Settings.getSettings().getLanguageClass().COUNTRY_COLOMBIA),
	croatia(12, "Croatia", Settings.getSettings().getLanguageClass().COUNTRY_CROATIA),
	cuba(13, "Cuba", Settings.getSettings().getLanguageClass().COUNTRY_CUBA),
	czechrepublic(14, "CzechRepublic", Settings.getSettings().getLanguageClass().COUNTRY_CZECHREPUBLIC),
	czechoslovakia(15, "Czechoslovakia", Settings.getSettings().getLanguageClass().COUNTRY_CZECHOSLOVAKIA),
	denmark(16, "Denmark", Settings.getSettings().getLanguageClass().COUNTRY_DENMARK),
	eastgermany(17, "EastGermany", Settings.getSettings().getLanguageClass().COUNTRY_EASTGERMANY),
	egypt(18, "Egypt", Settings.getSettings().getLanguageClass().COUNTRY_EGYPT),
	finland(19, "Finland", Settings.getSettings().getLanguageClass().COUNTRY_FINLAND),
	france(20, "France", Settings.getSettings().getLanguageClass().COUNTRY_FRANCE),
	germany(21, "Germany", Settings.getSettings().getLanguageClass().COUNTRY_GERMANY),
	greece(22, "Greece", Settings.getSettings().getLanguageClass().COUNTRY_GREECE),
	hongkong(23, "HongKong", Settings.getSettings().getLanguageClass().COUNTRY_HONGKONG),
	hungary(24, "Hungary", Settings.getSettings().getLanguageClass().COUNTRY_HUNGARY),
	india(25, "India", Settings.getSettings().getLanguageClass().COUNTRY_INDIA),
	indonesia(26, "Indonesia", Settings.getSettings().getLanguageClass().COUNTRY_INDONESIA),
	iran(27, "Iran", Settings.getSettings().getLanguageClass().COUNTRY_IRAN),
	ireland(28, "Ireland", Settings.getSettings().getLanguageClass().COUNTRY_IRELAND),
	israel(29, "Israel", Settings.getSettings().getLanguageClass().COUNTRY_ISRAEL),
	italy(30, "Italy", Settings.getSettings().getLanguageClass().COUNTRY_ITALY),
	japan(31, "Japan", Settings.getSettings().getLanguageClass().COUNTRY_JAPAN),
	mexico(32, "Mexico", Settings.getSettings().getLanguageClass().COUNTRY_MEXICO),
	netherlands(33, "Netherlands", Settings.getSettings().getLanguageClass().COUNTRY_NETHERLANDS),
	newzealand(34, "NewZealand", Settings.getSettings().getLanguageClass().COUNTRY_NEWZEALAND),
	nigeria(35, "Nigeria", Settings.getSettings().getLanguageClass().COUNTRY_NIGERIA),
	norway(36, "Norway", Settings.getSettings().getLanguageClass().COUNTRY_NORWAY),
	philippines(37, "Philippines", Settings.getSettings().getLanguageClass().COUNTRY_PHILIPPINES),
	poland(38, "Poland", Settings.getSettings().getLanguageClass().COUNTRY_POLAND),
	portugal(39, "Portugal", Settings.getSettings().getLanguageClass().COUNTRY_PORTUGAL),
	romania(40, "Romania", Settings.getSettings().getLanguageClass().COUNTRY_ROMANIA),
	russia(41, "Russia", Settings.getSettings().getLanguageClass().COUNTRY_RUSSIA),
	southafrica(42, "SouthAfrica", Settings.getSettings().getLanguageClass().COUNTRY_SOUTHAFRICA),
	southkorea(43, "SouthKorea", Settings.getSettings().getLanguageClass().COUNTRY_SOUTHKOREA),
	sovietunion(44, "SovietUnion", Settings.getSettings().getLanguageClass().COUNTRY_SOVIETUNION),
	spain(45, "Spain", Settings.getSettings().getLanguageClass().COUNTRY_SPAIN),
	sweden(46, "Sweden", Settings.getSettings().getLanguageClass().COUNTRY_SWEDEN),
	switzerland(47, "Switzerland", Settings.getSettings().getLanguageClass().COUNTRY_SWITZERLAND),
	taiwan(48, "Taiwan", Settings.getSettings().getLanguageClass().COUNTRY_TAIWAN),
	turkey(49, "Turkey", Settings.getSettings().getLanguageClass().COUNTRY_TURKEY),
	uk(50, "UK", Settings.getSettings().getLanguageClass().COUNTRY_UK),
	usa(51, "USA", Settings.getSettings().getLanguageClass().COUNTRY_USA),
	venezuela(52, "Venezuela", Settings.getSettings().getLanguageClass().COUNTRY_VENEZUELA),
	westgermany(53, "WestGermany", Settings.getSettings().getLanguageClass().COUNTRY_WESTGERMANY),
	yugoslavia(54, "Yugoslavia", Settings.getSettings().getLanguageClass().COUNTRY_YUGOSLAVIA),
	afghanistan(55, "Afghanistan", Settings.getSettings().getLanguageClass().COUNTRY_AFGHANISTAN),
	algeria(56, "Algeria", Settings.getSettings().getLanguageClass().COUNTRY_ALGERIA),
	andorra(57, "Andorra", Settings.getSettings().getLanguageClass().COUNTRY_ANDORRA),
	angola(58, "Angola", Settings.getSettings().getLanguageClass().COUNTRY_ANGOLA),
	antiguaandbarbuda(59, "AntiguaandBarbuda", Settings.getSettings().getLanguageClass().COUNTRY_ANTIGUAANDBARBUDA),
	armenia(60, "Armenia", Settings.getSettings().getLanguageClass().COUNTRY_ARMENIA),
	aruba(61, "Aruba", Settings.getSettings().getLanguageClass().COUNTRY_ARUBA),
	azerbaijan(62, "Azerbaijan", Settings.getSettings().getLanguageClass().COUNTRY_AZERBAIJAN),
	bahamas(63, "Bahamas", Settings.getSettings().getLanguageClass().COUNTRY_BAHAMAS),
	bahrain(64, "Bahrain", Settings.getSettings().getLanguageClass().COUNTRY_BAHRAIN),
	bangladesh(65, "Bangladesh", Settings.getSettings().getLanguageClass().COUNTRY_BANGLADESH),
	barbados(66, "Barbados", Settings.getSettings().getLanguageClass().COUNTRY_BARBADOS),
	belarus(67, "Belarus", Settings.getSettings().getLanguageClass().COUNTRY_BELARUS),
	belize(68, "Belize", Settings.getSettings().getLanguageClass().COUNTRY_BELIZE),
	benin(69, "Benin", Settings.getSettings().getLanguageClass().COUNTRY_BENIN),
	bhutan(70, "Bhutan", Settings.getSettings().getLanguageClass().COUNTRY_BHUTAN),
	bolivia(71, "Bolivia", Settings.getSettings().getLanguageClass().COUNTRY_BOLIVIA),
	bosnia_herzegovina(72, "Bosnia-Herzegovina", Settings.getSettings().getLanguageClass().COUNTRY_BOSNIA_HERZEGOVINA),
	botswana(73, "Botswana", Settings.getSettings().getLanguageClass().COUNTRY_BOTSWANA),
	burkinafaso(74, "BurkinaFaso", Settings.getSettings().getLanguageClass().COUNTRY_BURKINAFASO),
	burma(75, "Burma", Settings.getSettings().getLanguageClass().COUNTRY_BURMA),
	burundi(76, "Burundi", Settings.getSettings().getLanguageClass().COUNTRY_BURUNDI),
	cambodia(77, "Cambodia", Settings.getSettings().getLanguageClass().COUNTRY_CAMBODIA),
	cameroon(78, "Cameroon", Settings.getSettings().getLanguageClass().COUNTRY_CAMEROON),
	capeverde(79, "CapeVerde", Settings.getSettings().getLanguageClass().COUNTRY_CAPEVERDE),
	centralafricanrepublic(80, "CentralAfricanRepublic", Settings.getSettings().getLanguageClass().COUNTRY_CENTRALAFRICANREPUBLIC),
	chad(81, "Chad", Settings.getSettings().getLanguageClass().COUNTRY_CHAD),
	congo(82, "Congo", Settings.getSettings().getLanguageClass().COUNTRY_CONGO),
	costarica(83, "CostaRica", Settings.getSettings().getLanguageClass().COUNTRY_COSTARICA),
	cyprus(84, "Cyprus", Settings.getSettings().getLanguageClass().COUNTRY_CYPRUS),
	democraticrepublicofcongo(85, "DemocraticRepublicofCongo", Settings.getSettings().getLanguageClass().COUNTRY_DEMOCRATICREPUBLICOFCONGO),
	djibouti(86, "Djibouti", Settings.getSettings().getLanguageClass().COUNTRY_DJIBOUTI),
	dominicanrepublic(87, "DominicanRepublic", Settings.getSettings().getLanguageClass().COUNTRY_DOMINICANREPUBLIC),
	ecuador(88, "Ecuador", Settings.getSettings().getLanguageClass().COUNTRY_ECUADOR),
	elsalvador(89, "ElSalvador", Settings.getSettings().getLanguageClass().COUNTRY_ELSALVADOR),
	eritrea(90, "Eritrea", Settings.getSettings().getLanguageClass().COUNTRY_ERITREA),
	estonia(91, "Estonia", Settings.getSettings().getLanguageClass().COUNTRY_ESTONIA),
	ethiopia(92, "Ethiopia", Settings.getSettings().getLanguageClass().COUNTRY_ETHIOPIA),
	faroeislands(93, "FaroeIslands", Settings.getSettings().getLanguageClass().COUNTRY_FAROEISLANDS),
	federalrepublicofyugoslavia(94, "FederalRepublicofYugoslavia", Settings.getSettings().getLanguageClass().COUNTRY_FEDERALREPUBLICOFYUGOSLAVIA),
	fiji(95, "Fiji", Settings.getSettings().getLanguageClass().COUNTRY_FIJI),
	gabon(96, "Gabon", Settings.getSettings().getLanguageClass().COUNTRY_GABON),
	georgia(97, "Georgia", Settings.getSettings().getLanguageClass().COUNTRY_GEORGIA),
	ghana(98, "Ghana", Settings.getSettings().getLanguageClass().COUNTRY_GHANA),
	greenland(99, "Greenland", Settings.getSettings().getLanguageClass().COUNTRY_GREENLAND),
	guadeloupe(100, "Guadeloupe", Settings.getSettings().getLanguageClass().COUNTRY_GUADELOUPE),
	guatemala(101, "Guatemala", Settings.getSettings().getLanguageClass().COUNTRY_GUATEMALA),
	guinea(102, "Guinea", Settings.getSettings().getLanguageClass().COUNTRY_GUINEA),
	guinea_bissau(103, "Guinea-Bissau", Settings.getSettings().getLanguageClass().COUNTRY_GUINEA_BISSAU),
	guyana(104, "Guyana", Settings.getSettings().getLanguageClass().COUNTRY_GUYANA),
	haiti(105, "Haiti", Settings.getSettings().getLanguageClass().COUNTRY_HAITI),
	honduras(106, "Honduras", Settings.getSettings().getLanguageClass().COUNTRY_HONDURAS),
	iceland(107, "Iceland", Settings.getSettings().getLanguageClass().COUNTRY_ICELAND),
	iraq(108, "Iraq", Settings.getSettings().getLanguageClass().COUNTRY_IRAQ),
	ivorycoast(109, "IvoryCoast", Settings.getSettings().getLanguageClass().COUNTRY_IVORYCOAST),
	jamaica(110, "Jamaica", Settings.getSettings().getLanguageClass().COUNTRY_JAMAICA),
	jordan(111, "Jordan", Settings.getSettings().getLanguageClass().COUNTRY_JORDAN),
	kazakhstan(112, "Kazakhstan", Settings.getSettings().getLanguageClass().COUNTRY_KAZAKHSTAN),
	kenya(113, "Kenya", Settings.getSettings().getLanguageClass().COUNTRY_KENYA),
	korea(114, "Korea", Settings.getSettings().getLanguageClass().COUNTRY_KOREA),
	kosovo(115, "Kosovo", Settings.getSettings().getLanguageClass().COUNTRY_KOSOVO),
	kuwait(116, "Kuwait", Settings.getSettings().getLanguageClass().COUNTRY_KUWAIT),
	kyrgyzstan(117, "Kyrgyzstan", Settings.getSettings().getLanguageClass().COUNTRY_KYRGYZSTAN),
	laos(118, "Laos", Settings.getSettings().getLanguageClass().COUNTRY_LAOS),
	latvia(119, "Latvia", Settings.getSettings().getLanguageClass().COUNTRY_LATVIA),
	lebanon(120, "Lebanon", Settings.getSettings().getLanguageClass().COUNTRY_LEBANON),
	lesotho(121, "Lesotho", Settings.getSettings().getLanguageClass().COUNTRY_LESOTHO),
	liberia(122, "Liberia", Settings.getSettings().getLanguageClass().COUNTRY_LIBERIA),
	libya(123, "Libya", Settings.getSettings().getLanguageClass().COUNTRY_LIBYA),
	liechtenstein(124, "Liechtenstein", Settings.getSettings().getLanguageClass().COUNTRY_LIECHTENSTEIN),
	lithuania(125, "Lithuania", Settings.getSettings().getLanguageClass().COUNTRY_LITHUANIA),
	luxembourg(126, "Luxembourg", Settings.getSettings().getLanguageClass().COUNTRY_LUXEMBOURG),
	macau(127, "Macau", Settings.getSettings().getLanguageClass().COUNTRY_MACAU),
	madagascar(128, "Madagascar", Settings.getSettings().getLanguageClass().COUNTRY_MADAGASCAR),
	malaysia(129, "Malaysia", Settings.getSettings().getLanguageClass().COUNTRY_MALAYSIA),
	mali(130, "Mali", Settings.getSettings().getLanguageClass().COUNTRY_MALI),
	malta(131, "Malta", Settings.getSettings().getLanguageClass().COUNTRY_MALTA),
	martinique(132, "Martinique", Settings.getSettings().getLanguageClass().COUNTRY_MARTINIQUE),
	mauritania(133, "Mauritania", Settings.getSettings().getLanguageClass().COUNTRY_MAURITANIA),
	mauritius(134, "Mauritius", Settings.getSettings().getLanguageClass().COUNTRY_MAURITIUS),
	moldova(135, "Moldova", Settings.getSettings().getLanguageClass().COUNTRY_MOLDOVA),
	monaco(136, "Monaco", Settings.getSettings().getLanguageClass().COUNTRY_MONACO),
	mongolia(137, "Mongolia", Settings.getSettings().getLanguageClass().COUNTRY_MONGOLIA),
	morocco(138, "Morocco", Settings.getSettings().getLanguageClass().COUNTRY_MOROCCO),
	mozambique(139, "Mozambique", Settings.getSettings().getLanguageClass().COUNTRY_MOZAMBIQUE),
	namibia(140, "Namibia", Settings.getSettings().getLanguageClass().COUNTRY_NAMIBIA),
	nepal(141, "Nepal", Settings.getSettings().getLanguageClass().COUNTRY_NEPAL),
	nicaragua(142, "Nicaragua", Settings.getSettings().getLanguageClass().COUNTRY_NICARAGUA),
	niger(143, "Niger", Settings.getSettings().getLanguageClass().COUNTRY_NIGER),
	niue(144, "Niue", Settings.getSettings().getLanguageClass().COUNTRY_NIUE),
	northkorea(145, "NorthKorea", Settings.getSettings().getLanguageClass().COUNTRY_NORTHKOREA),
	northvietnam(146, "NorthVietnam", Settings.getSettings().getLanguageClass().COUNTRY_NORTHVIETNAM),
	oman(147, "Oman", Settings.getSettings().getLanguageClass().COUNTRY_OMAN),
	pakistan(148, "Pakistan", Settings.getSettings().getLanguageClass().COUNTRY_PAKISTAN),
	palestine(149, "Palestine", Settings.getSettings().getLanguageClass().COUNTRY_PALESTINE),
	panama(150, "Panama", Settings.getSettings().getLanguageClass().COUNTRY_PANAMA),
	papuanewguinea(151, "PapuaNewGuinea", Settings.getSettings().getLanguageClass().COUNTRY_PAPUANEWGUINEA),
	paraguay(152, "Paraguay", Settings.getSettings().getLanguageClass().COUNTRY_PARAGUAY),
	peru(153, "Peru", Settings.getSettings().getLanguageClass().COUNTRY_PERU),
	puertorico(154, "PuertoRico", Settings.getSettings().getLanguageClass().COUNTRY_PUERTORICO),
	qatar(155, "Qatar", Settings.getSettings().getLanguageClass().COUNTRY_QATAR),
	republicofmacedonia(156, "RepublicofMacedonia", Settings.getSettings().getLanguageClass().COUNTRY_REPUBLICOFMACEDONIA),
	rwanda(157, "Rwanda", Settings.getSettings().getLanguageClass().COUNTRY_RWANDA),
	sanmarino(158, "SanMarino", Settings.getSettings().getLanguageClass().COUNTRY_SANMARINO),
	saudiarabia(159, "SaudiArabia", Settings.getSettings().getLanguageClass().COUNTRY_SAUDIARABIA),
	senegal(160, "Senegal", Settings.getSettings().getLanguageClass().COUNTRY_SENEGAL),
	serbia(161, "Serbia", Settings.getSettings().getLanguageClass().COUNTRY_SERBIA),
	serbiaandmontenegro(162, "SerbiaandMontenegro", Settings.getSettings().getLanguageClass().COUNTRY_SERBIAANDMONTENEGRO),
	seychelles(163, "Seychelles", Settings.getSettings().getLanguageClass().COUNTRY_SEYCHELLES),
	siam(164, "Siam", Settings.getSettings().getLanguageClass().COUNTRY_SIAM),
	singapore(165, "Singapore", Settings.getSettings().getLanguageClass().COUNTRY_SINGAPORE),
	slovakia(166, "Slovakia", Settings.getSettings().getLanguageClass().COUNTRY_SLOVAKIA),
	slovenia(167, "Slovenia", Settings.getSettings().getLanguageClass().COUNTRY_SLOVENIA),
	somalia(168, "Somalia", Settings.getSettings().getLanguageClass().COUNTRY_SOMALIA),
	srilanka(169, "SriLanka", Settings.getSettings().getLanguageClass().COUNTRY_SRILANKA),
	sudan(170, "Sudan", Settings.getSettings().getLanguageClass().COUNTRY_SUDAN),
	suriname(171, "Suriname", Settings.getSettings().getLanguageClass().COUNTRY_SURINAME),
	syria(172, "Syria", Settings.getSettings().getLanguageClass().COUNTRY_SYRIA),
	tajikistan(173, "Tajikistan", Settings.getSettings().getLanguageClass().COUNTRY_TAJIKISTAN),
	tanzania(174, "Tanzania", Settings.getSettings().getLanguageClass().COUNTRY_TANZANIA),
	thailand(175, "Thailand", Settings.getSettings().getLanguageClass().COUNTRY_THAILAND),
	togo(176, "Togo", Settings.getSettings().getLanguageClass().COUNTRY_TOGO),
	tonga(177, "Tonga", Settings.getSettings().getLanguageClass().COUNTRY_TONGA),
	trinidadandtobago(178, "TrinidadAndTobago", Settings.getSettings().getLanguageClass().COUNTRY_TRINIDADANDTOBAGO),
	tunisia(179, "Tunisia", Settings.getSettings().getLanguageClass().COUNTRY_TUNISIA),
	turkmenistan(180, "Turkmenistan", Settings.getSettings().getLanguageClass().COUNTRY_TURKMENISTAN),
	uganda(181, "Uganda", Settings.getSettings().getLanguageClass().COUNTRY_UGANDA),
	ukraine(182, "Ukraine", Settings.getSettings().getLanguageClass().COUNTRY_UKRAINE),
	unitedarabemirates(183, "UnitedArabEmirates", Settings.getSettings().getLanguageClass().COUNTRY_UNITEDARABEMIRATES),
	uruguay(184, "Uruguay", Settings.getSettings().getLanguageClass().COUNTRY_URUGUAY),
	uzbekistan(185, "Uzbekistan", Settings.getSettings().getLanguageClass().COUNTRY_UZBEKISTAN),
	vietnam(186, "Vietnam", Settings.getSettings().getLanguageClass().COUNTRY_VIETNAM),
	westernsahara(187, "WesternSahara", Settings.getSettings().getLanguageClass().COUNTRY_WESTERNSAHARA),
	yemen(188, "Yemen", Settings.getSettings().getLanguageClass().COUNTRY_YEMEN),
	zaire(189, "Zaire", Settings.getSettings().getLanguageClass().COUNTRY_ZAIRE),
	zambia(190, "Zambia", Settings.getSettings().getLanguageClass().COUNTRY_ZAMBIA),
	zimbabwe(191, "Zimbabwe", Settings.getSettings().getLanguageClass().COUNTRY_ZIMBABWE);

	
	private int id;
	private String imdbID;
	private String name;
	
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
	
	public String getImdbID() {
		return imdbID;
	}
	
	public int getID() {
		return id;
	}

	public String getName() {
		return name;
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
}
