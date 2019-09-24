package iad.transact.reports.font;

import java.util.Map;

public class VerdanaSystemFontRegistryFactory extends SystemFontRegistryFactory {
  private static final String FONT_FAMILY_NAME = "Verdana";
  private static final Map<FontFaceType, String> FONT_FAMILY_FILENAME_MAP =
      Map.ofEntries(
          Map.entry(FontFaceType.NORMAL, "verdana.ttf"),
          Map.entry(FontFaceType.BOLD, "verdanab.ttf"),
          Map.entry(FontFaceType.ITALIC, "verdanai.ttf"),
          Map.entry(FontFaceType.BOLD_ITALIC, "verdanaz.ttf"));

  @Override
  protected String getFontFamilyName() {
    return FONT_FAMILY_NAME;
  }

  @Override
  protected Map<FontFaceType, String> getFontFamilyFilenameMap() {
    return FONT_FAMILY_FILENAME_MAP;
  }
}
