!include "MUI2.nsh"
!include "registerExtension.nsh"

;General
Name "JMoviedb"
Icon ""
OutFile "JMoviedb.exe"
InstallDir "$PROGRAMFILES\JMoviedb"

;Pages
!insertmacro MUI_PAGE_WELCOME

!insertmacro MUI_PAGE_LICENSE "gpl-3.0.txt"
!insertmacro MUI_PAGE_LICENSE "epl-1.0.txt"

!insertmacro MUI_PAGE_DIRECTORY
!insertmacro MUI_PAGE_COMPONENTS
!insertmacro MUI_PAGE_INSTFILES

!insertmacro MUI_UNPAGE_CONFIRM
!insertmacro MUI_UNPAGE_INSTFILES

;Languages
!insertmacro MUI_LANGUAGE "English"
!insertmacro MUI_LANGUAGE "Norwegian"

;Installer Functions
Function .onInit
	!insertmacro MUI_LANGDLL_DISPLAY
FunctionEnd

;Uninstaller Functions
Function un.onInit
	!insertmacro MUI_UNGETLANGUAGE
FunctionEnd

InstType "Default"

;Installer sections
Section "JMoviedb (required)" secMain
	SectionIn 1 RO
	SetOutPath "$INSTDIR"
	
	File ""
	WriteUninstaller "$INSTDIR\Uninstall.exe"
	${registerExtension} "c:\myplayer.exe" ".mkv" "MKV File"
SectionEnd

Section "Start Menu Shortcuts" secStartmenu
	SectionIn 1
	CreateShortCut "$SMPROGRAMS\JMoviedb.lnk" "$INSTDIR\program.exe" \
		"command line parameters" "icon file" 0
SectionEnd

Section "Desktop Shortcuts" secDesktop
	CreateShortCut "$DESKTOP\JMoviedb.lnk" "$INSTDIR\program.exe" \
		"command line parameters" "icon file" 0
SectionEnd

Section "Quick Launch Shortcuts" secQuicklaunch
	CreateShortCut "$QUICKLAUNCH\JMoviedb.lnk" "$INSTDIR\program.exe" \
		"command line parameters" "icon file" 0
SectionEnd

;Uninstall section
Section "Uninstall"
	Delete "$SMPROGRAMS\JMoviedb.lnk"
	Delete "$DESKTOP\JMoviedb.lnk"
	Delete "$QUICKLAUNCH\JMoviedb.lnk"
	Delete "$INSTDIR\..."
	Delete "$INSTDIR\Uninstall.exe"
	RMDir "$INSTDIR"
SectionEnd

;Assign descriptions to sections
LangString DESC_secMain ${LANG_ENGLISH} "JMoviedb"
LangString DESC_secMain ${LANG_NORWEGIAN} "JMoviedb"
LangString DESC_secStartmenu ${LANG_ENGLISH} "Add a shortcut to the start menu."
LangString DESC_secStartmenu ${LANG_NORWEGIAN} "Legg til en snarvei i startmenyen."
LangString DESC_secDesktop ${LANG_ENGLISH} "Add a shortcut to the desktop."
LangString DESC_secDesktop ${LANG_NORWEGIAN} "Legg til en snarvei på skrivebordet."
LangString DESC_secQuicklaunch ${LANG_ENGLISH} "Add a shortcut to the quick launch bar."
LangString DESC_secQuicklaunch ${LANG_NORWEGIAN} "Legg til en snarvei i hurtigstartlinjen."

!insertmacro MUI_FUNCTION_DESCRIPTION_BEGIN
	!insertmacro MUI_DESCRIPTION_TEXT ${secMain} $(DESC_secMain)
	!insertmacro MUI_DESCRIPTION_TEXT ${secStartmenu} $(DESC_secStartmenu)
	!insertmacro MUI_DESCRIPTION_TEXT ${secDesktop} $(DESC_secDesktop)
	!insertmacro MUI_DESCRIPTION_TEXT ${secQuicklaunch} $(DESC_secQuicklaunch)
!insertmacro MUI_FUNCTION_DESCRIPTION_END