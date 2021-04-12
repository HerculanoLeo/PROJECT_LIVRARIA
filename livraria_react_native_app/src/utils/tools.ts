import { NativeModules, Platform } from "react-native";

export const getLocale = () => {
  if (Platform.OS === 'ios') {
      const locale = NativeModules.SettingsManager.settings.AppleLocale || NativeModules.SettingsManager.settings.AppleLanguages[0];

      console.log('locale', locale);

      return locale;
  } else {
      const locale = NativeModules.I18nManager.localeIdentifier;

      console.log('locale', locale);

      return locale;
  }
}
