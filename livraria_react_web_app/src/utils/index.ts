import i18next from 'i18next';
import LanguageDetector from 'i18next-browser-languagedetector';

export interface TimerProps {
  cancel: () => void;
  pause: () => void;
  resume: () => void;
}

export function Timer(fn: any, countdown: number): TimerProps {
  let ident: any;
  let complete = false;
  let total_time_run: number;
  let start_time = new Date().getTime();

  function _time_diff(date1: number, date2?: number): number {
    return date2 ? date2 - date1 : new Date().getTime() - date1;
  }

  function cancel() {
    clearTimeout(ident);
  }

  function pause() {
    clearTimeout(ident);
    total_time_run = _time_diff(start_time);
    complete = total_time_run >= countdown;
  }

  function resume() {
    let time_remaining = countdown - total_time_run;

    let time = time_remaining < 0 ? 0 : time_remaining;

    if (complete) {
      fn();
    } else {
      ident = setTimeout(fn, time);
    }
  }

  ident = setTimeout(fn, countdown);

  return { cancel: cancel, pause: pause, resume: resume };
}

export const getLocale = () => {
  i18next.use(LanguageDetector).init();

  const language = i18next.language;
  
  return language;
}
