package selim.enderrifts.utils;

import java.util.Calendar;

import javax.annotation.Nonnull;

import net.minecraft.launchwrapper.Launch;

public class MiscUtils {

	public static boolean isDevEnvironment() {
		return (boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment");
	}

	@Nonnull
	public static EnumHoliday getHoliday() {
		Calendar cal = Calendar.getInstance();
		for (EnumHoliday holiday : EnumHoliday.values())
			if (holiday.isToday(cal))
				return holiday;
		return EnumHoliday.UNKNOWN;
	}

	public static boolean isHoliday(EnumHoliday holiday) {
		return holiday.isToday(Calendar.getInstance());
	}

	public static enum EnumHoliday {
		CHRISTMAS(new Day(Calendar.DECEMBER, 25)),
		APRIL_FOOLS(new Day(Calendar.APRIL, 1)),
		VALENTINES(new Day(Calendar.FEBRUARY, 14)),
		HALLOWEEN(new Day(Calendar.OCTOBER, 31)),
		BIRTHDAY(new Day(Calendar.JUNE, 21), new Day(Calendar.AUGUST, 18), new Day(Calendar.MAY, 8)),
		UNKNOWN;

		private final Day[] days;

		private EnumHoliday(Day... days) {
			this.days = days;
		}

		public boolean isToday(Calendar cal) {
			for (Day d : this.days)
				if (cal.get(Calendar.MONTH) == d.month && cal.get(Calendar.DAY_OF_MONTH) == d.day)
					return true;
			return false;
		}
	}

	private static class Day {

		private final int month;
		private final int day;

		private Day(int month, int day) {
			this.month = month;
			this.day = day;
		}

	}

}
