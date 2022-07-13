import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Customer {
	private String name;

	private List<Rental> rentals = new ArrayList<Rental>();

	public Customer(String name) {
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void clearRentals() {
		rentals.clear();
	}

	public void addRental(Rental rental) {
		rentals.add(rental);
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentals = getRentals();

		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental each : rentals) {
			double eachCharge = calculateChage(each);
			int eachPoint = calculatePoint(each);
			int daysRented = each.getDaysRented();

			result += "\t" + each.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";
			totalPoint += eachPoint ;
			totalCharge += eachCharge;
		}

		printCouponInfo(totalPoint);

		result += "Total charge: " + totalCharge + "\tTotal Point:" + totalPoint + "\n";
		return result ;
	}

	double calculateChage(Rental each) {
		double eachCharge = 0;
		int daysRented = each.getDaysRented();

		switch (each.getVideo().getPriceCode()) {
			case Video.REGULAR:
				eachCharge += 2;
				if (daysRented > 2)
					eachCharge += (daysRented - 2) * 1.5;
				break;
			case Video.NEW_RELEASE:
				eachCharge = daysRented * 3;
				break;
		}
		return eachCharge;
	}

	int calculatePoint(Rental each) {
		int eachPoint = 0 ;
		int daysRented = each.getDaysRented();
		eachPoint++;

		if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE) )
			eachPoint++;

		if ( daysRented > each.getDaysRentedLimit() )
			eachPoint -= Math.min(eachPoint, each.getVideo().getLateReturnPointPenalty()) ;


		return eachPoint;
	}

	void printCouponInfo(int totalPoint) {
		if ( totalPoint >= 10 ) {
			System.out.println("Congrat! You earned one free coupon");
		}
		if ( totalPoint >= 30 ) {
			System.out.println("Congrat! You earned two free coupon");
		}
	}
}
