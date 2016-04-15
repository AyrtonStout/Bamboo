package Systems;

public enum PartyMemberEnum {

	//    Name,    Gender,  Str, Agi, Int, Spi, Lck, Sta, Spe,  pStr pAgi pInt pSpi pLck pSta
	SABIN("Sabin", "Male", 8, 4, 5, 4, 7, 6, 9, 3, 6, 4, 0, 5, 5),
	TERRA("Terra", "Female", 4, 5, 6, 8, 8, 5, 8, 6, 6, 9, 2, 7, 1),
	CELES("Celes", "Female", 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5),
	LOCKE("Locke", "Male", 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5),
	CYAN("Cyan", "Male", 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5),
	GHOST("Ghost", "Male", 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5);

	private String name, gender;
	private int strength, agility, intellect, spirit, luck, stamina, speed;     //full values for a stat
	private int pStrength, pAgility, pIntellect, pSpirit, pLuck, pStamina;        //partial values for a stat (speed cant be partial)

	private PartyMemberEnum(String name, String gender, int str, int agi, int inte, int spi, int luck, int sta, int spe,
							int pStr, int pAgi, int pInt, int pSpi, int pLck, int pSta) {
		this.name = name;
		this.gender = gender;
		this.strength = str;
		this.agility = agi;
		this.intellect = inte;
		this.spirit = spi;
		this.luck = luck;
		this.stamina = sta;
		this.speed = spe;
	}

	public String getName() {
		return name;
	}

	public String getGender() {
		return gender;
	}

	public int getStrength() {
		return strength;
	}

	public int getAgility() {
		return agility;
	}

	public int getIntellect() {
		return intellect;
	}

	public int getSpirit() {
		return spirit;
	}

	public int getLuck() {
		return luck;
	}

	public int getStamina() {
		return stamina;
	}

	public int getSpeed() {
		return speed;
	}

	public int getpStrength() {
		return pStrength;
	}

	public int getpAgility() {
		return pAgility;
	}

	public int getpIntellect() {
		return pIntellect;
	}

	public int getpSpirit() {
		return pSpirit;
	}

	public int getpLuck() {
		return pLuck;
	}

	public int getpStamina() {
		return pStamina;
	}

}
