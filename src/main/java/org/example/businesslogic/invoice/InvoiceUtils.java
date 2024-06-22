package org.example.businesslogic.invoice;

import org.example.builder.InvoiceFormatBuilder;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class InvoiceUtils {
    private static final String lineFromSymbolX = "XXXXXXXXXX";

    public static String buildCarInvoiceString(Invoice invoice) {
        InvoiceFormatBuilder builder = new InvoiceFormatBuilder();
        builder.base();
        builder.baseAdditionalNoPenaltiesOrDiscounts();
        if (invoice.isReturnedEarly()) {
            builder.returnEarlyDiscount();
        }
        builder.footer();

        if(invoice.isReturnedEarly())
        return String.format(builder.build(), lineFromSymbolX,
                LocalDate.now(),
                invoice.getCustomer().nameCombination(),
                invoice.getVehicle().getBrand(),
                invoice.getVehicle().getModel(),
                invoice.getDateConfig().getReserveDate(),
                invoice.getDateConfig().getExpectedReturnDate(),
                invoice.reservedRentalDays(),
                invoice.getDateConfig().getActualReturnDate(),
                invoice.actualRentalDays(),
                invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                invoice.getVehicle().calculateInsuranceCostPerDay(),
                invoice.earlyReturnRateDiscount(),
                invoice.earlyInsuranceDiscount(),
                invoice.getFinalRentalCost(),
                invoice.getFinalRentalCost(),
                (invoice.getFinalRentalCost() + invoice.getFinalInsuranceCost()),
                lineFromSymbolX);

        return String.format(builder.build(), lineFromSymbolX,
                LocalDate.now(),
                invoice.getCustomer().nameCombination(),
                invoice.getVehicle().getBrand(),
                invoice.getVehicle().getModel(),
                invoice.getDateConfig().getReserveDate(),
                invoice.getDateConfig().getExpectedReturnDate(),
                invoice.reservedRentalDays(),
                invoice.getDateConfig().getActualReturnDate(),
                invoice.actualRentalDays(),
                invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                invoice.getVehicle().calculateInsuranceCostPerDay(),
                invoice.getFinalRentalCost(),
                invoice.getFinalRentalCost(),
                (invoice.getFinalRentalCost() + invoice.getFinalInsuranceCost()),
                lineFromSymbolX);
    }

    public static String buildCargoVanInvoiceString(Invoice invoice) {
        InvoiceFormatBuilder builder = new InvoiceFormatBuilder();
        builder.base();
        builder.experienceDiscount();
        builder.baseAdditionalNoPenaltiesOrDiscounts();
        if (invoice.isReturnedEarly()) {
            builder.returnEarlyDiscount();
        }

        builder.footer();

        if (invoice.isReturnedEarly()) {
            return String.format(builder.build(), lineFromSymbolX,
                    LocalDate.now(),
                    invoice.getCustomer().nameCombination(),
                    invoice.getVehicle().getBrand(),
                    invoice.getVehicle().getModel(),
                    invoice.getDateConfig().getReserveDate(),
                    invoice.getDateConfig().getExpectedReturnDate(),
                    invoice.reservedRentalDays(),
                    invoice.getDateConfig().getActualReturnDate(),
                    invoice.actualRentalDays(),
                    invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                    invoice.getVehicle().calculateInsuranceCostPerDay(),
                    invoice.insuranceDiscountForExperiencePerDay(),
                    (invoice.baseInsuranceCostWithExperiencePerDay()),
                    invoice.earlyReturnRateDiscount(),
                    invoice.earlyInsuranceDiscount(),
                    invoice.getFinalRentalCost(),
                    invoice.baseInsuranceCostWithExperiencePerDay() * invoice.actualRentalDays(),
                    (invoice.getFinalRentalCost() + invoice.baseInsuranceCostWithExperiencePerDay() * invoice.actualRentalDays()),
                    lineFromSymbolX);
        }

        return String.format(builder.build(), lineFromSymbolX,
                LocalDate.now(),
                invoice.getCustomer().nameCombination(),
                invoice.getVehicle().getBrand(),
                invoice.getVehicle().getModel(),
                invoice.getDateConfig().getReserveDate(),
                invoice.getDateConfig().getExpectedReturnDate(),
                invoice.reservedRentalDays(),
                invoice.getDateConfig().getActualReturnDate(),
                invoice.actualRentalDays(),
                invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                invoice.getVehicle().calculateInsuranceCostPerDay(),
                invoice.insuranceDiscountForExperiencePerDay(),
                (invoice.baseInsuranceCostWithExperiencePerDay()),
                invoice.getFinalRentalCost(),
                invoice.baseInsuranceCostWithExperiencePerDay() * invoice.actualRentalDays(),
                (invoice.getFinalRentalCost() + invoice.baseInsuranceCostWithExperiencePerDay() * invoice.actualRentalDays()),
                lineFromSymbolX);
    }

    public static String buildMotorcycleInvoiceString(Invoice invoice) {
        InvoiceFormatBuilder builder = new InvoiceFormatBuilder();
        builder.base();
        builder.underAgedDriver();
        builder.baseAdditionalNoPenaltiesOrDiscounts();
        if (invoice.isReturnedEarly()) {
            builder.returnEarlyDiscount();
        }

        builder.footer();

        if (invoice.isReturnedEarly()) {
            return String.format(builder.build(), lineFromSymbolX,
                    LocalDate.now(),
                    invoice.getCustomer().nameCombination(),
                    invoice.getVehicle().getBrand(),
                    invoice.getVehicle().getModel(),
                    invoice.getDateConfig().getReserveDate(),
                    invoice.getDateConfig().getExpectedReturnDate(),
                    invoice.reservedRentalDays(),
                    invoice.getDateConfig().getActualReturnDate(),
                    invoice.actualRentalDays(),
                    invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                    invoice.getVehicle().calculateInsuranceCostPerDay(),
                    invoice.additionalInsuranceFeeMotorcyclePerDay(),
                    (invoice.getVehicle().calculateInsuranceCostPerDay() + invoice.additionalInsuranceFeeMotorcyclePerDay()),
                    invoice.earlyReturnRateDiscount(),
                    invoice.earlyInsuranceDiscount(),
                    invoice.getFinalRentalCost(),
                    invoice.getFinalInsuranceCost(),
                    (invoice.getFinalRentalCost() + invoice.getFinalInsuranceCost()),
                    lineFromSymbolX);
        }

        return String.format(builder.build(), lineFromSymbolX,
                LocalDate.now(),
                invoice.getCustomer().nameCombination(),
                invoice.getVehicle().getBrand(),
                invoice.getVehicle().getModel(),
                invoice.getDateConfig().getReserveDate(),
                invoice.getDateConfig().getExpectedReturnDate(),
                invoice.reservedRentalDays(),
                invoice.getDateConfig().getActualReturnDate(),
                invoice.actualRentalDays(),
                invoice.getVehicle().calculateRentalCostPerDay(invoice.isRentedMoreThanWeek()),
                invoice.getVehicle().calculateInsuranceCostPerDay(),
                invoice.additionalInsuranceFeeMotorcyclePerDay(),
                (invoice.getVehicle().calculateInsuranceCostPerDay() + invoice.additionalInsuranceFeeMotorcyclePerDay()),
                invoice.getFinalRentalCost(),
                invoice.getFinalInsuranceCost(),
                (invoice.getFinalRentalCost() + invoice.getFinalInsuranceCost()),
                lineFromSymbolX);
    }

    public static boolean isRentPeriodMoreThanWeek(LocalDate start, LocalDate end) {
        return DAYS.between(start, end) > 7;
    }

}