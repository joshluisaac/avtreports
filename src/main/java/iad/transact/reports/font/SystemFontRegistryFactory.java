package iad.transact.reports.font;

import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.platform.win32.Shell32;
import com.sun.jna.platform.win32.ShlObj;
import com.sun.jna.platform.win32.W32Errors;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.platform.win32.WinNT;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.sf.jasperreports.engine.DefaultJasperReportsContext;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JasperReportsContext;
import net.sf.jasperreports.engine.fonts.FontFamily;
import net.sf.jasperreports.engine.fonts.SimpleFontFace;
import net.sf.jasperreports.engine.fonts.SimpleFontFamily;
import net.sf.jasperreports.extensions.ExtensionsRegistry;
import net.sf.jasperreports.extensions.ExtensionsRegistryFactory;

public abstract class SystemFontRegistryFactory implements ExtensionsRegistryFactory {
  private static final String UNIX_MSTTCOREFONTS_DIR = "/usr/share/fonts/msttcorefonts/";

  protected abstract String getFontFamilyName();

  protected abstract Map<FontFaceType, String> getFontFamilyFilenameMap();

  @Override
  public ExtensionsRegistry createRegistry(String registryId, JRPropertiesMap properties) {
    return new SystemFontExtensionsRegistry(getFontFamilyName(), getFontFamilyFilenameMap());
  }

  private class SystemFontExtensionsRegistry implements ExtensionsRegistry {

    private final String fontFamilyName;
    private final Map<FontFaceType, String> fontFaceFilenames;
    private List<FontFamily> fontFamilies;

    public SystemFontExtensionsRegistry(
        String fontFamilyName, Map<FontFaceType, String> fontFaceFilenames) {
      this.fontFamilyName = fontFamilyName;
      this.fontFaceFilenames = fontFaceFilenames;
    }

    @Override
    public <T> List<T> getExtensions(Class<T> extensionType) {
      if (FontFamily.class.equals(extensionType)) {
        @SuppressWarnings("unchecked")
        List<T> extensions = (List<T>) loadFontExtensions();
        return extensions;
      }

      return null;
    }

    private List<FontFamily> loadFontExtensions() {
      if (fontFamilies == null) {
        DefaultJasperReportsContext context = DefaultJasperReportsContext.getInstance();

        SimpleFontFamily fontFamily = new SimpleFontFamily(context);
        fontFamily.setName(fontFamilyName);
        fontFamily.setNormalFace(loadFontFace(context, fontFaceFilenames.get(FontFaceType.NORMAL)));
        fontFamily.setBoldFace(loadFontFace(context, fontFaceFilenames.get(FontFaceType.BOLD)));
        fontFamily.setItalicFace(loadFontFace(context, fontFaceFilenames.get(FontFaceType.ITALIC)));
        fontFamily.setBoldItalicFace(
            loadFontFace(context, fontFaceFilenames.get(FontFaceType.BOLD_ITALIC)));

        fontFamilies = new ArrayList<>();
        fontFamilies.add(fontFamily);
      }

      return fontFamilies;
    }

    private String getSystemFontPath() {
      if (Platform.isWindows()) {
        char[] pszPath = new char[WinDef.MAX_PATH];
        WinNT.HRESULT hResult =
            Shell32.INSTANCE.SHGetFolderPath(
                null, ShlObj.CSIDL_FONTS, null, ShlObj.SHGFP_TYPE_CURRENT, pszPath);

        if (!W32Errors.SUCCEEDED(hResult)) {
          throw new RuntimeException("Failed to get font folder");
        }
        return Native.toString(pszPath) + File.separator;
      } else {
        return UNIX_MSTTCOREFONTS_DIR;
      }
    }

    private SimpleFontFace loadFontFace(JasperReportsContext context, String fontFaceFilename) {
      SimpleFontFace fontFace = new SimpleFontFace(context);
      fontFace.setTtf(getSystemFontPath() + fontFaceFilename, true);
      return fontFace;
    }
  }

  protected enum FontFaceType {
    NORMAL,
    BOLD,
    ITALIC,
    BOLD_ITALIC
  }
}
