/**
* Copyright (c) eTree Technologies
*
* @author  Franklin Abel
* @version 1.0
* @since   2020-06-08 
*
* This file is part of the etree-ref-data.
* 
*  The OTC framework is free software: you can redistribute it and/or modify
*  it under the terms of the GNU General Public License as published by
*  the Free Software Foundation, version 3 of the License.
*
*  The OTC framework is distributed in the hope that it will be useful,
*  but WITHOUT ANY WARRANTY; without even the implied warranty of
*  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
*  GNU General Public License for more details.
*
*  A copy of the GNU General Public License is made available as 'License.md' file, 
*  along with OTC framework project.  If not, see <https://www.gnu.org/licenses/>.
*
*/
package etree.refdata.common.dto;

public class CountriesDto implements OpendataDtoBase {
	private String isoCodeAlpha2;
	private String isoCodeAlpha3;
	private String isoCodeNumeric;
	private String countryName;
	private String isdCode;
	private String status;
	private String currencyCode;
	private String regionCode;

	public String getIsoCodeAlpha2() {
		return isoCodeAlpha2;
	}

	public void setIsoCodeAlpha2(String isoCodeAlpha2) {
		this.isoCodeAlpha2 = isoCodeAlpha2;
	}

	public String getIsoCodeAlpha3() {
		return isoCodeAlpha3;
	}

	public void setIsoCodeAlpha3(String isoCodeAlpha3) {
		this.isoCodeAlpha3 = isoCodeAlpha3;
	}

	public String getCountryName() {
		return countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getIsoCodeNumeric() {
		return isoCodeNumeric;
	}

	public void setIsoCodeNumeric(String isoCodeNumeric) {
		this.isoCodeNumeric = isoCodeNumeric;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}
}
