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

/**
 * An enum containing all the languages assigned to movies at IMDb.
 * @author Tor
 *
 */
public enum Language {
	none(0, "None", Settings.getSettings().getLanguageClass().LANGUAGE_NONE, "xx"),
	albanian(1, "Albanian", Settings.getSettings().getLanguageClass().LANGUAGE_ALBANIAN, "sq"),
	arabic(2, "Arabic", Settings.getSettings().getLanguageClass().LANGUAGE_ARABIC, "ar"),
	bengali(3, "Bengali", Settings.getSettings().getLanguageClass().LANGUAGE_BENGALI, "bn"),
	bulgarian(4, "Bulgarian", Settings.getSettings().getLanguageClass().LANGUAGE_BULGARIAN, "bg"),
	cantonese(5, "Cantonese", Settings.getSettings().getLanguageClass().LANGUAGE_CANTONESE, "cn"),
	catalan(6, "Catalan", Settings.getSettings().getLanguageClass().LANGUAGE_CATALAN, "ca"),
	czech(7, "Czech", Settings.getSettings().getLanguageClass().LANGUAGE_CZECH, "cs"),
	danish(8, "Danish", Settings.getSettings().getLanguageClass().LANGUAGE_DANISH, "da"),
	dutch(9, "Dutch", Settings.getSettings().getLanguageClass().LANGUAGE_DUTCH, "nl"),
	english(10, "English", Settings.getSettings().getLanguageClass().LANGUAGE_ENGLISH, "en"),
	filipino(11, "Filipino", Settings.getSettings().getLanguageClass().LANGUAGE_FILIPINO),
	finnish(12, "Finnish", Settings.getSettings().getLanguageClass().LANGUAGE_FINNISH, "fi"),
	french(13, "French", Settings.getSettings().getLanguageClass().LANGUAGE_FRENCH, "fr"),
	georgian(14, "Georgian", Settings.getSettings().getLanguageClass().LANGUAGE_GEORGIAN, "ka"),
	german(15, "German", Settings.getSettings().getLanguageClass().LANGUAGE_GERMAN, "de"),
	greek(16, "Greek", Settings.getSettings().getLanguageClass().LANGUAGE_GREEK, "el"),
	hebrew(17, "Hebrew", Settings.getSettings().getLanguageClass().LANGUAGE_HEBREW, "he"),
	hindi(18, "Hindi", Settings.getSettings().getLanguageClass().LANGUAGE_HINDI, "hi"),
	hungarian(19, "Hungarian", Settings.getSettings().getLanguageClass().LANGUAGE_HUNGARIAN, "hu"),
	italian(20, "Italian", Settings.getSettings().getLanguageClass().LANGUAGE_ITALIAN, "it"),
	japanese(21, "Japanese", Settings.getSettings().getLanguageClass().LANGUAGE_JAPANESE, "ja"),
	korean(22, "Korean", Settings.getSettings().getLanguageClass().LANGUAGE_KOREAN, "ko"),
	malayalam(23, "Malayalam", Settings.getSettings().getLanguageClass().LANGUAGE_MALAYALAM, "ml"),
	mandarin(24, "Mandarin", Settings.getSettings().getLanguageClass().LANGUAGE_MANDARIN, "zh"),
	marathi(25, "Marathi", Settings.getSettings().getLanguageClass().LANGUAGE_MARATHI, "mr"),
	norwegian(27, "Norwegian", Settings.getSettings().getLanguageClass().LANGUAGE_NORWEGIAN, "no"),
	persian(28, "Persian", Settings.getSettings().getLanguageClass().LANGUAGE_PERSIAN, "fa"),
	polish(29, "Polish", Settings.getSettings().getLanguageClass().LANGUAGE_POLISH, "pl"),
	portuguese(30, "Portuguese", Settings.getSettings().getLanguageClass().LANGUAGE_PORTUGUESE, "pt"),
	romanian(31, "Romanian", Settings.getSettings().getLanguageClass().LANGUAGE_ROMANIAN, "ro"),
	russian(32, "Russian", Settings.getSettings().getLanguageClass().LANGUAGE_RUSSIAN, "ru"),
	serbo_croatian(33, "Serbo-Croatian", Settings.getSettings().getLanguageClass().LANGUAGE_SERBO_CROATIAN, "sh"),
	spanish(34, "Spanish", Settings.getSettings().getLanguageClass().LANGUAGE_SPANISH, "es"),
	swedish(35, "Swedish", Settings.getSettings().getLanguageClass().LANGUAGE_SWEDISH, "sv"),
	tagalog(36, "Tagalog", Settings.getSettings().getLanguageClass().LANGUAGE_TAGALOG, "tl"),
	tamil(37, "Tamil", Settings.getSettings().getLanguageClass().LANGUAGE_TAMIL, "ta"),
	telugu(38, "Telugu", Settings.getSettings().getLanguageClass().LANGUAGE_TELUGU, "te"),
	turkish(39, "Turkish", Settings.getSettings().getLanguageClass().LANGUAGE_TURKISH, "tr"),
	aboriginal(40, "Aboriginal", Settings.getSettings().getLanguageClass().LANGUAGE_ABORIGINAL),
	acholi(41, "Acholi", Settings.getSettings().getLanguageClass().LANGUAGE_ACHOLI),
	ache(42, "Aché", Settings.getSettings().getLanguageClass().LANGUAGE_ACHE),
	afrikaans(43, "Afrikaans", Settings.getSettings().getLanguageClass().LANGUAGE_AFRIKAANS, "af"),
	aidoukrou(44, "Aidoukrou", Settings.getSettings().getLanguageClass().LANGUAGE_AIDOUKROU),
	akan(45, "Akan", Settings.getSettings().getLanguageClass().LANGUAGE_AKAN, "ak"),
	algonquin(46, "Algonquin", Settings.getSettings().getLanguageClass().LANGUAGE_ALGONQUIN),
	alsatian(47, "Alsatian", Settings.getSettings().getLanguageClass().LANGUAGE_ALSATIAN),
	americansignlanguage(48, "AmericanSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_AMERICANSIGNLANGUAGE),
	amharic(49, "Amharic", Settings.getSettings().getLanguageClass().LANGUAGE_AMHARIC, "am"),
	aragonese(50, "Aragonese", Settings.getSettings().getLanguageClass().LANGUAGE_ARAGONESE, "an"),
	aramaic(51, "Aramaic", Settings.getSettings().getLanguageClass().LANGUAGE_ARAMAIC),
	arapaho(52, "Arapaho", Settings.getSettings().getLanguageClass().LANGUAGE_ARAPAHO),
	armenian(53, "Armenian", Settings.getSettings().getLanguageClass().LANGUAGE_ARMENIAN, "hy"),
	assamese(54, "Assamese", Settings.getSettings().getLanguageClass().LANGUAGE_ASSAMESE, "as"),
	assyrianneo_aramaic(55, "AssyrianNeo-Aramaic", Settings.getSettings().getLanguageClass().LANGUAGE_ASSYRIANNEO_ARAMAIC),
	australiansignlanguage(56, "AustralianSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_AUSTRALIANSIGNLANGUAGE),
	awadhi(57, "Awadhi", Settings.getSettings().getLanguageClass().LANGUAGE_AWADHI),
	aymara(58, "Aymara", Settings.getSettings().getLanguageClass().LANGUAGE_AYMARA, "ay"),
	azeri(59, "Azeri", Settings.getSettings().getLanguageClass().LANGUAGE_AZERI),
	babledialect(60, "Babledialect", Settings.getSettings().getLanguageClass().LANGUAGE_BABLEDIALECT),
	baka(61, "Baka", Settings.getSettings().getLanguageClass().LANGUAGE_BAKA),
	bambara(62, "Bambara", Settings.getSettings().getLanguageClass().LANGUAGE_BAMBARA, "bm"),
	basque(63, "Basque", Settings.getSettings().getLanguageClass().LANGUAGE_BASQUE, "eu"),
	bassari(64, "Bassari", Settings.getSettings().getLanguageClass().LANGUAGE_BASSARI),
	belarusian(65, "Belarusian", Settings.getSettings().getLanguageClass().LANGUAGE_BELARUSIAN, "be"),
	bemba(66, "Bemba", Settings.getSettings().getLanguageClass().LANGUAGE_BEMBA),
	berber(67, "Berber", Settings.getSettings().getLanguageClass().LANGUAGE_BERBER),
	bhojpuri(68, "Bhojpuri", Settings.getSettings().getLanguageClass().LANGUAGE_BHOJPURI),
	bicolano(69, "Bicolano", Settings.getSettings().getLanguageClass().LANGUAGE_BICOLANO),
	bodo(70, "Bodo", Settings.getSettings().getLanguageClass().LANGUAGE_BODO),
	braziliansignlanguage(71, "BrazilianSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_BRAZILIANSIGNLANGUAGE),
	breton(72, "Breton", Settings.getSettings().getLanguageClass().LANGUAGE_BRETON, "br"),
	britishsignlanguage(73, "BritishSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_BRITISHSIGNLANGUAGE),
	burmese(74, "Burmese", Settings.getSettings().getLanguageClass().LANGUAGE_BURMESE, "my"),
	chaozhou(75, "Chaozhou", Settings.getSettings().getLanguageClass().LANGUAGE_CHAOZHOU),
	chechen(76, "Chechen", Settings.getSettings().getLanguageClass().LANGUAGE_CHECHEN, "ce"),
	cherokee(77, "Cherokee", Settings.getSettings().getLanguageClass().LANGUAGE_CHEROKEE),
	cheyenne(78, "Cheyenne", Settings.getSettings().getLanguageClass().LANGUAGE_CHEYENNE),
	chhattisgarhi(79, "Chhattisgarhi", Settings.getSettings().getLanguageClass().LANGUAGE_CHHATTISGARHI),
	cornish(80, "Cornish", Settings.getSettings().getLanguageClass().LANGUAGE_CORNISH, "kw"),
	corsican(81, "Corsican", Settings.getSettings().getLanguageClass().LANGUAGE_CORSICAN, "co"),
	cree(82, "Cree", Settings.getSettings().getLanguageClass().LANGUAGE_CREE, "cr"),
	croatian(292, "Croatian", "Croatian", "hr"),
	crow(83, "Crow", Settings.getSettings().getLanguageClass().LANGUAGE_CROW),
	dari(84, "Dari", Settings.getSettings().getLanguageClass().LANGUAGE_DARI),
	desia(85, "Desia", Settings.getSettings().getLanguageClass().LANGUAGE_DESIA),
	dinka(86, "Dinka", Settings.getSettings().getLanguageClass().LANGUAGE_DINKA),
	dioula(87, "Dioula", Settings.getSettings().getLanguageClass().LANGUAGE_DIOULA),
	djerma(88, "Djerma", Settings.getSettings().getLanguageClass().LANGUAGE_DJERMA),
	dogri(89, "Dogri", Settings.getSettings().getLanguageClass().LANGUAGE_DOGRI),
	dzongkha(90, "Dzongkha", Settings.getSettings().getLanguageClass().LANGUAGE_DZONGKHA, "dz"),
	east_greenlandic(91, "East-Greenlandic", Settings.getSettings().getLanguageClass().LANGUAGE_EAST_GREENLANDIC),
	esperanto(92, "Esperanto", Settings.getSettings().getLanguageClass().LANGUAGE_ESPERANTO, "eo"),
	estonian(93, "Estonian", Settings.getSettings().getLanguageClass().LANGUAGE_ESTONIAN, "et"),
	faliasch(94, "Faliasch", Settings.getSettings().getLanguageClass().LANGUAGE_FALIASCH),
	faroese(95, "Faroese", Settings.getSettings().getLanguageClass().LANGUAGE_FAROESE, "fo"),
	flemish(96, "Flemish", Settings.getSettings().getLanguageClass().LANGUAGE_FLEMISH),
	fon(97, "Fon", Settings.getSettings().getLanguageClass().LANGUAGE_FON),
	frenchsignlanguage(98, "FrenchSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_FRENCHSIGNLANGUAGE),
	frisian(99, "Frisian", Settings.getSettings().getLanguageClass().LANGUAGE_FRISIAN, "fy"),
	fula(100, "Fula", Settings.getSettings().getLanguageClass().LANGUAGE_FULA),
	gaelic(101, "Gaelic", Settings.getSettings().getLanguageClass().LANGUAGE_GAELIC, "gd"),
	galician(102, "Galician", Settings.getSettings().getLanguageClass().LANGUAGE_GALICIAN, "gl"),
	germansignlanguage(103, "GermanSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_GERMANSIGNLANGUAGE),
	grebo(104, "Grebo", Settings.getSettings().getLanguageClass().LANGUAGE_GREBO),
	greenlandic(105, "Greenlandic", Settings.getSettings().getLanguageClass().LANGUAGE_GREENLANDIC),
	guarani(106, "Guarani", Settings.getSettings().getLanguageClass().LANGUAGE_GUARANI, "gn"),
	gujarati(107, "Gujarati", Settings.getSettings().getLanguageClass().LANGUAGE_GUJARATI, "gu"),
	gumatj(108, "Gumatj", Settings.getSettings().getLanguageClass().LANGUAGE_GUMATJ),
	haitian(109, "Haitian", Settings.getSettings().getLanguageClass().LANGUAGE_HAITIAN),
	hakka(110, "Hakka", Settings.getSettings().getLanguageClass().LANGUAGE_HAKKA),
	haryanvi(111, "Haryanvi", Settings.getSettings().getLanguageClass().LANGUAGE_HARYANVI),
	hassanya(112, "Hassanya", Settings.getSettings().getLanguageClass().LANGUAGE_HASSANYA),
	hausa(113, "Hausa", Settings.getSettings().getLanguageClass().LANGUAGE_HAUSA, "ha"),
	hawaiian(114, "Hawaiian", Settings.getSettings().getLanguageClass().LANGUAGE_HAWAIIAN),
	hokkien(115, "Hokkien", Settings.getSettings().getLanguageClass().LANGUAGE_HOKKIEN),
	hopi(116, "Hopi", Settings.getSettings().getLanguageClass().LANGUAGE_HOPI),
	iban(117, "Iban", Settings.getSettings().getLanguageClass().LANGUAGE_IBAN),
	ibo(118, "Ibo", Settings.getSettings().getLanguageClass().LANGUAGE_IBO),
	icelandic(119, "Icelandic", Settings.getSettings().getLanguageClass().LANGUAGE_ICELANDIC, "is"),
	icelandicsignlanguage(120, "IcelandicSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_ICELANDICSIGNLANGUAGE),
	indiansignlanguage(121, "IndianSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_INDIANSIGNLANGUAGE),
	indonesian(122, "Indonesian", Settings.getSettings().getLanguageClass().LANGUAGE_INDONESIAN, "id"),
	inuktitut(123, "Inuktitut", Settings.getSettings().getLanguageClass().LANGUAGE_INUKTITUT, "iu"),
	irishgaelic(124, "IrishGaelic", Settings.getSettings().getLanguageClass().LANGUAGE_IRISHGAELIC),
	japanesesignlanguage(125, "JapaneseSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_JAPANESESIGNLANGUAGE),
	juhoan(126, "Juhoan", Settings.getSettings().getLanguageClass().LANGUAGE_JUHOAN),
	kaado(127, "Kaado", Settings.getSettings().getLanguageClass().LANGUAGE_KAADO),
	kabuverdianu(128, "Kabuverdianu", Settings.getSettings().getLanguageClass().LANGUAGE_KABUVERDIANU),
	kabyle(129, "Kabyle", Settings.getSettings().getLanguageClass().LANGUAGE_KABYLE),
	kalmyk_oirat(130, "Kalmyk-Oirat", Settings.getSettings().getLanguageClass().LANGUAGE_KALMYK_OIRAT),
	kannada(131, "Kannada", Settings.getSettings().getLanguageClass().LANGUAGE_KANNADA, "kn"),
	karaja(132, "Karaja", Settings.getSettings().getLanguageClass().LANGUAGE_KARAJA),
	karbi(133, "Karbi", Settings.getSettings().getLanguageClass().LANGUAGE_KARBI),
	kazakh(134, "Kazakh", Settings.getSettings().getLanguageClass().LANGUAGE_KAZAKH, "kk"),
	khanty(135, "Khanty", Settings.getSettings().getLanguageClass().LANGUAGE_KHANTY),
	khasi(136, "Khasi", Settings.getSettings().getLanguageClass().LANGUAGE_KHASI),
	khmer(137, "Khmer", Settings.getSettings().getLanguageClass().LANGUAGE_KHMER, "km"),
	kikongo(138, "Kikongo", Settings.getSettings().getLanguageClass().LANGUAGE_KIKONGO),
	kinyarwanda(139, "Kinyarwanda", Settings.getSettings().getLanguageClass().LANGUAGE_KINYARWANDA, "rw"),
	kirundi(140, "Kirundi", Settings.getSettings().getLanguageClass().LANGUAGE_KIRUNDI),
	klingon(141, "Klingon", Settings.getSettings().getLanguageClass().LANGUAGE_KLINGON),
	kodava(142, "Kodava", Settings.getSettings().getLanguageClass().LANGUAGE_KODAVA),
	konkani(143, "Konkani", Settings.getSettings().getLanguageClass().LANGUAGE_KONKANI),
	koreansignlanguage(144, "KoreanSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_KOREANSIGNLANGUAGE),
	korowai(145, "Korowai", Settings.getSettings().getLanguageClass().LANGUAGE_KOROWAI),
	kriolu(146, "Kriolu", Settings.getSettings().getLanguageClass().LANGUAGE_KRIOLU),
	kru(147, "Kru", Settings.getSettings().getLanguageClass().LANGUAGE_KRU),
	kuna(148, "Kuna", Settings.getSettings().getLanguageClass().LANGUAGE_KUNA),
	kunwinjku(149, "Kunwinjku", Settings.getSettings().getLanguageClass().LANGUAGE_KUNWINJKU),
	kurdish(150, "Kurdish", Settings.getSettings().getLanguageClass().LANGUAGE_KURDISH, "ku"),
	kwakiutl(151, "Kwakiutl", Settings.getSettings().getLanguageClass().LANGUAGE_KWAKIUTL),
	kyrgyz(152, "Kyrgyz", Settings.getSettings().getLanguageClass().LANGUAGE_KYRGYZ),
	ladakhi(153, "Ladakhi", Settings.getSettings().getLanguageClass().LANGUAGE_LADAKHI),
	ladino(154, "Ladino", Settings.getSettings().getLanguageClass().LANGUAGE_LADINO),
	lao(155, "Lao", Settings.getSettings().getLanguageClass().LANGUAGE_LAO, "lo"),
	latin(156, "Latin", Settings.getSettings().getLanguageClass().LANGUAGE_LATIN, "la"),
	latvian(157, "Latvian", Settings.getSettings().getLanguageClass().LANGUAGE_LATVIAN, "lv"),
	lenguayorubaantigua(158, "LenguaYorubaAntigua", Settings.getSettings().getLanguageClass().LANGUAGE_LENGUAYORUBAANTIGUA),
	letzebuergesh(159, "Letzebuergesh", Settings.getSettings().getLanguageClass().LANGUAGE_LETZEBUERGESH),
	limbu(160, "Limbu", Settings.getSettings().getLanguageClass().LANGUAGE_LIMBU),
	lingala(161, "Lingala", Settings.getSettings().getLanguageClass().LANGUAGE_LINGALA, "ln"),
	lithuanian(162, "Lithuanian", Settings.getSettings().getLanguageClass().LANGUAGE_LITHUANIAN, "lt"),
	maasai(291, "Maasai", "Maasai"),
	macedonian(163, "Macedonian", Settings.getSettings().getLanguageClass().LANGUAGE_MACEDONIAN, "mk"),
	macro_je(164, "Macro-Jê", Settings.getSettings().getLanguageClass().LANGUAGE_MACRO_JE),
	magahi(165, "Magahi", Settings.getSettings().getLanguageClass().LANGUAGE_MAGAHI),
	maithili(166, "Maithili", Settings.getSettings().getLanguageClass().LANGUAGE_MAITHILI),
	malagasy(167, "Malagasy", Settings.getSettings().getLanguageClass().LANGUAGE_MALAGASY, "mg"),
	malay(168, "Malay", Settings.getSettings().getLanguageClass().LANGUAGE_MALAY, "ms"),
	malecite_passamaquoddy(169, "Malecite-Passamaquoddy", Settings.getSettings().getLanguageClass().LANGUAGE_MALECITE_PASSAMAQUODDY),
	malinka(170, "Malinka", Settings.getSettings().getLanguageClass().LANGUAGE_MALINKA),
	maltese(171, "Maltese", Settings.getSettings().getLanguageClass().LANGUAGE_MALTESE, "mt"),
	manchu(172, "Manchu", Settings.getSettings().getLanguageClass().LANGUAGE_MANCHU),
	mandingo(173, "Mandingo", Settings.getSettings().getLanguageClass().LANGUAGE_MANDINGO),
	manipuri(174, "Manipuri", Settings.getSettings().getLanguageClass().LANGUAGE_MANIPURI),
	maori(175, "Maori", Settings.getSettings().getLanguageClass().LANGUAGE_MAORI, "mi"),
	mapudungun(176, "Mapudungun", Settings.getSettings().getLanguageClass().LANGUAGE_MAPUDUNGUN),
	marshallese(177, "Marshallese", Settings.getSettings().getLanguageClass().LANGUAGE_MARSHALLESE),
	masalit(178, "Masalit", Settings.getSettings().getLanguageClass().LANGUAGE_MASALIT),
	maya(179, "Maya", Settings.getSettings().getLanguageClass().LANGUAGE_MAYA),
	mende(180, "Mende", Settings.getSettings().getLanguageClass().LANGUAGE_MENDE),
	micmac(181, "Micmac", Settings.getSettings().getLanguageClass().LANGUAGE_MICMAC),
	middleenglish(182, "MiddleEnglish", Settings.getSettings().getLanguageClass().LANGUAGE_MIDDLEENGLISH),
	minnan(183, "MinNan", Settings.getSettings().getLanguageClass().LANGUAGE_MINNAN),
	mizo(184, "Mizo", Settings.getSettings().getLanguageClass().LANGUAGE_MIZO),
	mohawk(185, "Mohawk", Settings.getSettings().getLanguageClass().LANGUAGE_MOHAWK),
	mongolian(186, "Mongolian", Settings.getSettings().getLanguageClass().LANGUAGE_MONGOLIAN, "mn"),
	montagnais(187, "Montagnais", Settings.getSettings().getLanguageClass().LANGUAGE_MONTAGNAIS),
	more(188, "More", Settings.getSettings().getLanguageClass().LANGUAGE_MORE),
	morisyen(189, "Morisyen", Settings.getSettings().getLanguageClass().LANGUAGE_MORISYEN),
	moso(190, "Moso", Settings.getSettings().getLanguageClass().LANGUAGE_MOSO),
	nagpuri(191, "Nagpuri", Settings.getSettings().getLanguageClass().LANGUAGE_NAGPURI),
	nahuatl(192, "Nahuatl", Settings.getSettings().getLanguageClass().LANGUAGE_NAHUATL),
	nama(193, "Nama", Settings.getSettings().getLanguageClass().LANGUAGE_NAMA),
	navajo(194, "Navajo", Settings.getSettings().getLanguageClass().LANGUAGE_NAVAJO, "nv"),
	ndebeleS(195, "South Ndebele", Settings.getSettings().getLanguageClass().LANGUAGE_NDEBELE, "nr"),
	nenets(196, "Nenets", Settings.getSettings().getLanguageClass().LANGUAGE_NENETS),
	nepali(197, "Nepali", Settings.getSettings().getLanguageClass().LANGUAGE_NEPALI, "ne"),
	nisgaa(198, "Nisgaa", Settings.getSettings().getLanguageClass().LANGUAGE_NISGAA),
	nushi(199, "Nushi", Settings.getSettings().getLanguageClass().LANGUAGE_NUSHI),
	nyaneka(200, "Nyaneka", Settings.getSettings().getLanguageClass().LANGUAGE_NYANEKA),
	nyanja(201, "Nyanja", Settings.getSettings().getLanguageClass().LANGUAGE_NYANJA),
	occitan(202, "Occitan", Settings.getSettings().getLanguageClass().LANGUAGE_OCCITAN, "oc"),
	ojibwa(203, "Ojibwa", Settings.getSettings().getLanguageClass().LANGUAGE_OJIBWA, "oj"),
	ojihimba(204, "Ojihimba", Settings.getSettings().getLanguageClass().LANGUAGE_OJIHIMBA),
	oldenglish(205, "OldEnglish", Settings.getSettings().getLanguageClass().LANGUAGE_OLDENGLISH),
	oriya(206, "Oriya", Settings.getSettings().getLanguageClass().LANGUAGE_ORIYA, "or"),
	papiamento(207, "Papiamento", Settings.getSettings().getLanguageClass().LANGUAGE_PAPIAMENTO),
	parsee(208, "Parsee", Settings.getSettings().getLanguageClass().LANGUAGE_PARSEE),
	pashtu(209, "Pashtu", Settings.getSettings().getLanguageClass().LANGUAGE_PASHTU),
	pawnee(210, "Pawnee", Settings.getSettings().getLanguageClass().LANGUAGE_PAWNEE),
	peul(211, "Peul", Settings.getSettings().getLanguageClass().LANGUAGE_PEUL),
	polynesian(212, "Polynesian", Settings.getSettings().getLanguageClass().LANGUAGE_POLYNESIAN),
	provencal(213, "Provençal", Settings.getSettings().getLanguageClass().LANGUAGE_PROVENCAL),
	pular(214, "Pular", Settings.getSettings().getLanguageClass().LANGUAGE_PULAR),
	punjabi(215, "Punjabi", Settings.getSettings().getLanguageClass().LANGUAGE_PUNJABI, "pa"),
	purepecha(216, "Purépecha", Settings.getSettings().getLanguageClass().LANGUAGE_PUREPECHA),
	quechua(217, "Quechua", Settings.getSettings().getLanguageClass().LANGUAGE_QUECHUA, "qu"),
	quenya(218, "Quenya", Settings.getSettings().getLanguageClass().LANGUAGE_QUENYA),
	rajasthani(219, "Rajasthani", Settings.getSettings().getLanguageClass().LANGUAGE_RAJASTHANI),
	rawan(220, "Rawan", Settings.getSettings().getLanguageClass().LANGUAGE_RAWAN),
	rhaeto_romanic(221, "Rhaeto-Romanic", Settings.getSettings().getLanguageClass().LANGUAGE_RHAETO_ROMANIC),
	romany(222, "Romany", Settings.getSettings().getLanguageClass().LANGUAGE_ROMANY),
	rotuman(223, "Rotuman", Settings.getSettings().getLanguageClass().LANGUAGE_ROTUMAN),
	russiansignlanguage(224, "RussianSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_RUSSIANSIGNLANGUAGE),
	ryukyuan(225, "Ryukyuan", Settings.getSettings().getLanguageClass().LANGUAGE_RYUKYUAN),
	saami(226, "Saami", Settings.getSettings().getLanguageClass().LANGUAGE_SAAMI),
	samoan(227, "Samoan", Settings.getSettings().getLanguageClass().LANGUAGE_SAMOAN, "sm"),
	sanskrit(228, "Sanskrit", Settings.getSettings().getLanguageClass().LANGUAGE_SANSKRIT, "sa"),
	sardinian(229, "Sardinian", Settings.getSettings().getLanguageClass().LANGUAGE_SARDINIAN, "sc"),
	scanian(230, "Scanian", Settings.getSettings().getLanguageClass().LANGUAGE_SCANIAN),
	scottishgaelic(231, "ScottishGaelic", Settings.getSettings().getLanguageClass().LANGUAGE_SCOTTISHGAELIC),
	serbian(293, "Serbian", "Serbian", "sr"),
	serere(232, "Serere", Settings.getSettings().getLanguageClass().LANGUAGE_SERERE),
	shanghainese(233, "Shanghainese", Settings.getSettings().getLanguageClass().LANGUAGE_SHANGHAINESE),
	shanxi(234, "Shanxi", Settings.getSettings().getLanguageClass().LANGUAGE_SHANXI),
	shona(235, "Shona", Settings.getSettings().getLanguageClass().LANGUAGE_SHONA, "sn"),
	shoshone(236, "Shoshone", Settings.getSettings().getLanguageClass().LANGUAGE_SHOSHONE),
	sicilian(237, "Sicilian", Settings.getSettings().getLanguageClass().LANGUAGE_SICILIAN),
	sindarin(238, "Sindarin", Settings.getSettings().getLanguageClass().LANGUAGE_SINDARIN),
	sindhi(239, "Sindhi", Settings.getSettings().getLanguageClass().LANGUAGE_SINDHI, "sd"),
	sinhala(240, "Sinhala", Settings.getSettings().getLanguageClass().LANGUAGE_SINHALA),
	sioux(241, "Sioux", Settings.getSettings().getLanguageClass().LANGUAGE_SIOUX),
	skawkaren(242, "SkawKaren", Settings.getSettings().getLanguageClass().LANGUAGE_SKAWKAREN),
	slovak(243, "Slovak", Settings.getSettings().getLanguageClass().LANGUAGE_SLOVAK, "sk"),
	slovenian(244, "Slovenian", Settings.getSettings().getLanguageClass().LANGUAGE_SLOVENIAN, "sl"),
	somali(245, "Somali", Settings.getSettings().getLanguageClass().LANGUAGE_SOMALI, "so"),
	songhay(246, "Songhay", Settings.getSettings().getLanguageClass().LANGUAGE_SONGHAY),
	soninke(247, "Soninke", Settings.getSettings().getLanguageClass().LANGUAGE_SONINKE),
	sotho(248, "Sotho", Settings.getSettings().getLanguageClass().LANGUAGE_SOTHO, "st"),
	sousson(249, "Sousson", Settings.getSettings().getLanguageClass().LANGUAGE_SOUSSON),
	spanishsignlanguage(250, "SpanishSignLanguage", Settings.getSettings().getLanguageClass().LANGUAGE_SPANISHSIGNLANGUAGE),
	sranan(251, "Sranan", Settings.getSettings().getLanguageClass().LANGUAGE_SRANAN),
	swahili(252, "Swahili", Settings.getSettings().getLanguageClass().LANGUAGE_SWAHILI, "sw"),
	swissgerman(253, "SwissGerman", Settings.getSettings().getLanguageClass().LANGUAGE_SWISSGERMAN),
	sylhetti(254, "Sylhetti", Settings.getSettings().getLanguageClass().LANGUAGE_SYLHETTI),
	taiwanese(255, "Taiwanese", Settings.getSettings().getLanguageClass().LANGUAGE_TAIWANESE),
	tajik(256, "Tajik", Settings.getSettings().getLanguageClass().LANGUAGE_TAJIK, "tg"),
	tamashek(257, "Tamashek", Settings.getSettings().getLanguageClass().LANGUAGE_TAMASHEK),
	tarahumara(258, "Tarahumara", Settings.getSettings().getLanguageClass().LANGUAGE_TARAHUMARA),
	tatar(259, "Tatar", Settings.getSettings().getLanguageClass().LANGUAGE_TATAR, "tt"),
	teochew(260, "Teochew", Settings.getSettings().getLanguageClass().LANGUAGE_TEOCHEW),
	thai(261, "Thai", Settings.getSettings().getLanguageClass().LANGUAGE_THAI, "th"),
	tibetan(262, "Tibetan", Settings.getSettings().getLanguageClass().LANGUAGE_TIBETAN, "bo"),
	tigrigna(263, "Tigrigna", Settings.getSettings().getLanguageClass().LANGUAGE_TIGRIGNA),
	tlingit(264, "Tlingit", Settings.getSettings().getLanguageClass().LANGUAGE_TLINGIT),
	tongan(265, "Tongan", Settings.getSettings().getLanguageClass().LANGUAGE_TONGAN),
	tsonga(266, "Tsonga", Settings.getSettings().getLanguageClass().LANGUAGE_TSONGA, "ts"),
	tswa(267, "Tswa", Settings.getSettings().getLanguageClass().LANGUAGE_TSWA),
	tswana(268, "Tswana", Settings.getSettings().getLanguageClass().LANGUAGE_TSWANA, "tn"),
	tulu(269, "Tulu", Settings.getSettings().getLanguageClass().LANGUAGE_TULU),
	tupi(270, "Tupi", Settings.getSettings().getLanguageClass().LANGUAGE_TUPI),
	turkmen(271, "Turkmen", Settings.getSettings().getLanguageClass().LANGUAGE_TURKMEN, "tk"),
	tuvan(272, "Tuvan", Settings.getSettings().getLanguageClass().LANGUAGE_TUVAN),
	tzotzil(273, "Tzotzil", Settings.getSettings().getLanguageClass().LANGUAGE_TZOTZIL),
	ukrainian(274, "Ukrainian", Settings.getSettings().getLanguageClass().LANGUAGE_UKRAINIAN, "uk"),
	ungwatsi(275, "Ungwatsi", Settings.getSettings().getLanguageClass().LANGUAGE_UNGWATSI),
	urdu(276, "Urdu", Settings.getSettings().getLanguageClass().LANGUAGE_URDU, "ur"),
	uzbek(277, "Uzbek", Settings.getSettings().getLanguageClass().LANGUAGE_UZBEK, "uz"),
	valencian(278, "Valencian", Settings.getSettings().getLanguageClass().LANGUAGE_VALENCIAN),
	vietnamese(279, "Vietnamese", Settings.getSettings().getLanguageClass().LANGUAGE_VIETNAMESE, "vi"),
	visayan(280, "Visayan", Settings.getSettings().getLanguageClass().LANGUAGE_VISAYAN),
	washoe(281, "Washoe", Settings.getSettings().getLanguageClass().LANGUAGE_WASHOE),
	welsh(282, "Welsh", Settings.getSettings().getLanguageClass().LANGUAGE_WELSH, "cy"),
	wolof(283, "Wolof", Settings.getSettings().getLanguageClass().LANGUAGE_WOLOF, "wo"),
	xhosa(284, "Xhosa", Settings.getSettings().getLanguageClass().LANGUAGE_XHOSA, "xh"),
	xitewa(285, "Xitewa", Settings.getSettings().getLanguageClass().LANGUAGE_XITEWA),
	yakut(286, "Yakut", Settings.getSettings().getLanguageClass().LANGUAGE_YAKUT),
	yapese(287, "Yapese", Settings.getSettings().getLanguageClass().LANGUAGE_YAPESE),
	yiddish(288, "Yiddish", Settings.getSettings().getLanguageClass().LANGUAGE_YIDDISH, "yi"),
	yoruba(289, "Yoruba", Settings.getSettings().getLanguageClass().LANGUAGE_YORUBA, "yo"),
	zulu(290, "Zulu", Settings.getSettings().getLanguageClass().LANGUAGE_ZULU, "zu"),
	chinese(294, "Chinese", "Chinese", "zh"),
	chinesesimplified(295, "Chinese Simplified", "Chinese Simplified", "zh"),
	chinesetraditional(296, "Chinese Traditional", "Chinese Traditional", "zh"),
	ndebeleNorth(297, "North Ndebele", Settings.getSettings().getLanguageClass().LANGUAGE_NDEBELE, "nd"),
	norwegianBokmal(298, "Norwegian Bokmål", "Norwegian Bokmål", "nb"),
	norwegianNynorsk(299, "Norwegian Nynorsk", "Norwegian Nynorsk", "nn");
	
	
	private int id;
	private String imdbID;
	private String name;
	private String iso639;
	
	/**
	 * Constructor.
	 * @param id a numerical ID, used for storage in the SQL database
	 * @param imdbID IMDb's ID for this language
	 * @param name the name of this language
	 */
	Language(int id, String imdbID, String name) {
		this.id = id;
		this.imdbID = imdbID;
		this.name = name;
	}

	Language(int id, String imdbID, String name, String iso639) {
		this(id, imdbID, name);
		this.iso639 = iso639;
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
	 * Converts a string to a Language enum, for example &quot;English&quot; to Language.English 
	 * @param string the string to be converted
	 * @return a Language enum, or null if there was no match
	 */
	public static Language StringToEnum(String string) {
		if(string == null) {
			return null;
		}
		for(Language l : Language.values())
			if(string.toLowerCase().equals(l.getImdbID().toLowerCase()))
				return l;
		return null;
	}
	
	/**
	 * Looks up the correct enum value from an int ID
	 * @param i the ID to look up
	 * @return the associated enum
	 */
	public static Language intToEnum(int id) {
		for(Language l : Language.values())
			if(id == l.getID())
				return l;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised language ID: " + id);
		return null;
	}
	
	public static String[] getStringArray() {
		String[] strings = new String[Language.values().length];
		for(int i = 0; i < Language.values().length; i++)
			strings[i] = Language.values()[i].getImdbID();
		return strings;
	}
	
	public static Language iso639ToEnum(String isoCode) {
		for(Language l : Language.values())
			if(l.iso639 != null && isoCode.equals(l.iso639))
				return l;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised ISO language code: " + isoCode);
		return null;
	}
	
	public static Language nameToEnum(String languageName) {
		for(Language l : Language.values())
			if(languageName.equals(l.name))
				return l;
		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised language name: " + languageName);
		return null;
	}
	
	public static Language tmdbLanguageToEnum(info.movito.themoviedbapi.model.Language tmdbLanguage) {
		if (tmdbLanguage.getName().equals("普通话"))
			return Language.mandarin;

		Language lan = iso639ToEnum(tmdbLanguage.getIsoCode());
		if (lan != null) return lan;

		lan = nameToEnum(tmdbLanguage.getName());
		if (lan != null) return lan;

		if(CONST.DEBUG_MODE)
			System.out.println("Unrecognised ISO language code: " + tmdbLanguage);
		return null;
	}
}
