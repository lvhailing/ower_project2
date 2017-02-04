package com.cf360.bean;

import com.cf360.mould.types.IMouldType;

public class ResultMyCustomerInfoDetailContentBean implements IMouldType {
	
	private String id;				//Id
	private String customerType;		//1:潜在客户;2意向客户;3成交客户
	private String code;		//客户编码
	private String name;		//客户姓名
	private String sex;		//客户性别man:男;women:女
	private String age;		//年龄
	private String idcard;		//身份证号
	private String nation;		//名族
	private String company;		//公司
	private String position;		//职位
	private String profession;		//所在行业
	private String homePhone;		//家庭电话
	private String mobilePhone;		//手机号码
	private String prePhone;		//备用电话
	private String qqNumber ;		//Qq
	private String fax;		//传真
	private String email;		//电子邮箱
	private String birthProvince;		//出生省份
	private String birthCity;		//出生城市
	private String liveProvince;		//居住省份
	private String liveCity;		//居住城市
	private String investType;		//投资类型
	private String investTrade;		//投资行业
	private String investMoney;		//投资金额
	private String investYear;		//投资年限
	private String investIncome;		//预期收益
	private String car;		//私家车
	private String house;		//房产
	private String valueAssessment;		//价值评估high:高;middle:中;low:低
	private String yearSalary;		//年薪
	private String interestSport;		//体育爱好  复选以“，“分隔 	1:篮球;2:足球;3:网球;4:赛车;5:游泳;6:健身;7:滑雪;8:羽毛球;9:乒乓球;10:保龄球;11:高尔夫;12:水上运动;13:其他
	private String interestArt;		//艺术爱好  复选以“，“分隔 	1:戏曲;2:歌剧;3:话剧;4:电影;5:文学;6:书法绘画;7:古典音乐;8:流行音乐;9:其他
	private String interestArder;		//休闲爱好  复选以“，“分隔	1:棋牌;2:登山;3:旅游;4:摄影;5:茗茶;6:骑马;7:钓鱼;8:园艺;9:古董收藏;10:博物展览;11:其他
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerType() {
		return customerType;
	}
	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getIdcard() {
		return idcard;
	}
	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}
	public String getNation() {
		return nation;
	}
	public void setNation(String nation) {
		this.nation = nation;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public String getProfession() {
		return profession;
	}
	public void setProfession(String profession) {
		this.profession = profession;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getMobilePhone() {
		return mobilePhone;
	}
	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}
	public String getPrePhone() {
		return prePhone;
	}
	public void setPrePhone(String prePhone) {
		this.prePhone = prePhone;
	}
	public String getQqNumber() {
		return qqNumber;
	}
	public void setQqNumber(String qqNumber) {
		this.qqNumber = qqNumber;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getBirthProvince() {
		return birthProvince;
	}
	public void setBirthProvince(String birthProvince) {
		this.birthProvince = birthProvince;
	}
	public String getBirthCity() {
		return birthCity;
	}
	public void setBirthCity(String birthCity) {
		this.birthCity = birthCity;
	}
	public String getLiveProvince() {
		return liveProvince;
	}
	public void setLiveProvince(String liveProvince) {
		this.liveProvince = liveProvince;
	}
	public String getLiveCity() {
		return liveCity;
	}
	public void setLiveCity(String liveCity) {
		this.liveCity = liveCity;
	}
	public String getInvestType() {
		return investType;
	}
	public void setInvestType(String investType) {
		this.investType = investType;
	}
	public String getInvestTrade() {
		return investTrade;
	}
	public void setInvestTrade(String investTrade) {
		this.investTrade = investTrade;
	}
	public String getInvestMoney() {
		return investMoney;
	}
	public void setInvestMoney(String investMoney) {
		this.investMoney = investMoney;
	}
	public String getInvestYear() {
		return investYear;
	}
	public void setInvestYear(String investYear) {
		this.investYear = investYear;
	}
	public String getInvestIncome() {
		return investIncome;
	}
	public void setInvestIncome(String investIncome) {
		this.investIncome = investIncome;
	}
	public String getCar() {
		return car;
	}
	public void setCar(String car) {
		this.car = car;
	}
	public String getHouse() {
		return house;
	}
	public void setHouse(String house) {
		this.house = house;
	}
	public String getValueAssessment() {
		return valueAssessment;
	}
	public void setValueAssessment(String valueAssessment) {
		this.valueAssessment = valueAssessment;
	}
	public String getYearSalary() {
		return yearSalary;
	}
	public void setYearSalary(String yearSalary) {
		this.yearSalary = yearSalary;
	}
	public String getInterestSport() {
		return interestSport;
	}
	public void setInterestSport(String interestSport) {
		this.interestSport = interestSport;
	}
	public String getInterestArt() {
		return interestArt;
	}
	public void setInterestArt(String interestArt) {
		this.interestArt = interestArt;
	}
	public String getInterestArder() {
		return interestArder;
	}
	public void setInterestArder(String interestArder) {
		this.interestArder = interestArder;
	}
	
	
}
