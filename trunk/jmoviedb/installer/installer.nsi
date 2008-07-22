!include "MUI2.nsh"
!include "registerExtension.nsh"
!define JAVA_HOME $R0
!define DETECTED_JRE_VERSION $R1
!define VAL1 $R2
!define VAL2 $R3
!define INSTALLDIR $R4
!define JRE_URL $R5
!define JRE_FILENAME $R6
!define JAVA_MESSAGE_ENG $R7
!define JAVA_MESSAGE_NOR $R8
!define JMOVIEDB_PLATFORM "32"
!define JMOVIEDB_VERSION "0.1.0"
!define REQUIRED_JRE_VERSION "1.5"
!define DOWNLOADED_JRE_VERSION "6 Update 3"

;General
Name "JMoviedb"
OutFile "JMoviedb-${JMOVIEDB_VERSION}-win${JMOVIEDB_PLATFORM}-installer.exe"

;Interface configuration
!define MUI_ICON "windows-icon.ico"
!define MUI_UNICON "windows-icon.ico"
!define MUI_WELCOMEFINISHPAGE_BITMAP "welcomepage.bmp"
!define MUI_UNWELCOMEFINISHPAGE_BITMAP "welcomepage.bmp"
!define MUI_HEADERIMAGE "headerimage.bmp"
!define MUI_HEADERIMAGE_BITMAP "headerimage.bmp"
!define MUI_HEADERIMAGE_UNBITMAP "headerimage.bmp"

;Pages
!insertmacro MUI_PAGE_WELCOME
!insertmacro MUI_PAGE_LICENSE "gpl-3.0.txt"
!insertmacro MUI_PAGE_LICENSE "epl-1.0.txt"
Page custom DetectJRE
!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_INSTFILES
!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;Languages
!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "Norwegian"

;Show language selector
Function .onInit
	Call chooseplatform
	ReadRegStr $0 HKLM "Software\JMoviedb" ""
	StrCmp $0 "" invalidReg
	StrCpy $INSTDIR $0
invalidReg:
	!insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

;configuration according to system architecture
Function chooseplatform
	SetRegView 32
	StrCmp ${JMOVIEDB_PLATFORM} "64" win64
	StrCpy $INSTDIR "$PROGRAMFILES\JMoviedb"
	StrCpy ${JRE_URL} "http://javadl.sun.com/webapps/download/AutoDL?BundleId=12798"
	StrCpy ${JRE_FILENAME} "jre-6u3-windows-i586-p-s.exe"
	StrCpy ${JAVA_MESSAGE_ENG}  "Java 1.5 (Java SE 5.0) or newer is required to run JMoviedb, but it was not detected on your computer. Download and install Java SE ${DOWNLOADED_JRE_VERSION} now?"
	StrCpy ${JAVA_MESSAGE_NOR} "Java 1.5 (Java SE 5.0) eller nyere kreves for å kjøre JMoviedb, men dette har ikke blitt oppdaget på datamaskinen. Vil du laste ned og installere Java SE ${DOWNLOADED_JRE_VERSION} nå?"
	Goto end64
win64:
	SetRegView 64
	StrCpy $INSTDIR "$PROGRAMFILES64\JMoviedb"
	StrCpy ${JRE_URL} "http://javadl.sun.com/webapps/download/AutoDL?BundleId=12796"
	StrCpy ${JRE_FILENAME} "jre-6u3-windows-amd64.exe"
	StrCpy ${JAVA_MESSAGE_ENG} "A 64-bit version of Java 1.5 (Java SE 5.0) or newer is required to run JMoviedb, but it was not detected on your computer. Download and install Java SE ${DOWNLOADED_JRE_VERSION} 64-bit now?"
	StrCpy ${JAVA_MESSAGE_NOR} "En 64-bit versjon av Java 1.5 (Java SE 5.0) eller nyere kreves for å kjøre JMoviedb, men dette har ikke blitt oppdaget på datamaskinen. Vil du laste ned og installere Java SE ${DOWNLOADED_JRE_VERSION} 64-bit nå?"
end64:
FunctionEnd

Function DetectJRE
	ReadRegStr ${DETECTED_JRE_VERSION} HKLM "SOFTWARE\JavaSoft\Java Runtime Environment" "CurrentVersion"
	StrCmp ${DETECTED_JRE_VERSION} "" Fail
	ReadRegStr ${JAVA_HOME} HKLM "SOFTWARE\JavaSoft\Java Runtime Environment\${DETECTED_JRE_VERSION}" "JavaHome"
	StrCmp ${JAVA_HOME} "" Fail
	IfFileExists "${JAVA_HOME}\bin\java.exe" 0 Fail
	
	StrCpy ${VAL1} ${DETECTED_JRE_VERSION} 1
	StrCpy ${VAL2} ${REQUIRED_JRE_VERSION} 1
	IntCmp ${VAL1} ${VAL2} 0 Fail Success
	StrCpy ${VAL1} ${DETECTED_JRE_VERSION} 1 2
	StrCpy ${VAL2} ${REQUIRED_JRE_VERSION} 1 2
	IntCmp ${VAL1} ${VAL2} Success Fail Success
	
Success:
	Push "${JAVA_HOME}\bin\java.exe"
	Push "OK"
	Return
	
Fail:
	Push "None"
	Push "NOK"
	Return
FunctionEnd

;Installer sections
Section "-Java" secJava
	Call DetectJRE

	Pop $0
	StrCmp $0 "OK" End
	
	MessageBox MB_YESNO $(JavaMessage) IDNO ExitInstallJRE
	
	NSISdl::download /TIMEOUT=30000 ${JRE_URL} "$TEMP\${JRE_FILENAME}"
    Pop $0 ;Get the return value
	StrCmp $0 "success" InstallJRE 0
    StrCmp $0 "cancel" 0 +3
    Push "Download cancelled."
    Goto ExitInstallJRE
    Push "Unkown error during download."
    Goto ExitInstallJRE

InstallJRE:
	DetailPrint "Launching Java setup"
	ExecWait "$TEMP\${JRE_FILENAME}" $0
	DetailPrint "Java setup finished"
	Delete "$TEMP\${JRE_FILENAME}"
	StrCmp $0 "0" InstallVerif 0
	Push "The JRE setup has been abnormally interrupted."
	Goto ExitInstallJRE

InstallVerif:
	DetailPrint "Checking the JRE Setup's outcome"
	Call DetectJRE
	Pop $0
	StrCmp $0 "OK" End
	Push "The JRE setup failed"
	Goto ExitInstallJRE

ExitInstallJRE:
	Quit
End:
SectionEnd

Section "JMoviedb ${JMOVIEDB_VERSION}" secMain
	SectionIn RO
	SetOutPath $INSTDIR
	
	File "JMoviedb.exe"
	File "JMoviedb.jar"
	WriteRegStr HKLM "Software\JMoviedb" "" $INSTDIR
	WriteRegStr HKLM "Software\JMoviedb" "Version" ${JMOVIEDB_VERSION}
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "DisplayName" "JMoviedb ${JMOVIEDB_VERSION}"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "UninstallString" "$INSTDIR\uninstall.exe"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "DisplayIcon" "$INSTDIR\JMoviedb.exe"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "URLInfoAbout" "http://jmoviedb.googlecode.com"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "URLUpdateInfo" "http://jmoviedb.googlecode.com"
	WriteRegStr HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "DisplayVersion" ${JMOVIEDB_VERSION}
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "NoModify" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "NoRepair" 1
	WriteRegDWORD HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" "EstimatedSize" 7168
	WriteUninstaller "$INSTDIR\uninstall.exe"
	${registerExtension} "$INSTDIR\JMoviedb.exe" ".jmdb" "JMoviedb file"
SectionEnd

Section "Start Menu Shortcut" secStartmenu
	SectionIn 1
	CreateShortCut "$SMPROGRAMS\JMoviedb.lnk" "$INSTDIR\JMoviedb.exe"
SectionEnd

Section "Desktop Shortcut" secDesktop
	CreateShortCut "$DESKTOP\JMoviedb.lnk" "$INSTDIR\JMoviedb.exe" 
SectionEnd

Section "Quick Launch Shortcut" secQuicklaunch
	CreateShortCut "$QUICKLAUNCH\JMoviedb.lnk" "$INSTDIR\JMoviedb.exe"
SectionEnd

;Uninstall section
Section "Uninstall"
	${unregisterExtension} ".jmdb" "JMoviedb file"
	Delete "$SMPROGRAMS\JMoviedb.lnk"
	Delete "$DESKTOP\JMoviedb.lnk"
	Delete "$QUICKLAUNCH\JMoviedb.lnk"
	Delete "$INSTDIR\JMoviedb.exe"
	Delete "$INSTDIR\JMoviedb.jar"
	Delete "$INSTDIR\uninstall.exe"
	RMDir "$INSTDIR"
	DeleteRegKey HKLM "Software\JMoviedb"
	DeleteRegKey HKLM "Software\Microsoft\Windows\CurrentVersion\Uninstall\JMoviedb" 
SectionEnd

;Assign descriptions to sections
LangString DESC_secStartmenu ${LANG_ENGLISH} "Add a shortcut to the start menu."
LangString DESC_secStartmenu ${LANG_NORWEGIAN} "Legg til en snarvei i startmenyen."
LangString DESC_secDesktop ${LANG_ENGLISH} "Add a shortcut to the desktop."
LangString DESC_secDesktop ${LANG_NORWEGIAN} "Legg til en snarvei på skrivebordet."
LangString DESC_secQuicklaunch ${LANG_ENGLISH} "Add a shortcut to the quick launch bar."
LangString DESC_secQuicklaunch ${LANG_NORWEGIAN} "Legg til en snarvei i hurtigstartlinjen."
LangString JavaMessage ${LANG_ENGLISH} ${JAVA_MESSAGE_ENG}
LangString JavaMessage ${LANG_NORWEGIAN} ${JAVA_MESSAGE_NOR}

!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
	!insertmacro MUI_DESCRIPTION_TEXT ${secMain} "JMoviedb ${JMOVIEDB_VERSION}"
	!insertmacro MUI_DESCRIPTION_TEXT ${secStartmenu} $(DESC_secStartmenu)
	!insertmacro MUI_DESCRIPTION_TEXT ${secDesktop} $(DESC_secDesktop)
	!insertmacro MUI_DESCRIPTION_TEXT ${secQuicklaunch} $(DESC_secQuicklaunch)
!insertmacro MUI_FUNCTION_DESCRIPTION_END
