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

import java.util.Date;

public class TimezoneDstDto implements OpendataDtoBase {
	String dst_id;
	String dst_name;
	String description;
	Date dst_start_date;
	Date dst_end_date;
	int dst_variation_minutes;
	String status;

	public String getDst_id() {
		return dst_id;
	}

	public void setDst_id(String dst_id) {
		this.dst_id = dst_id;
	}

	public String getDst_name() {
		return dst_name;
	}

	public void setDst_name(String dst_name) {
		this.dst_name = dst_name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getDst_start_date() {
		return dst_start_date;
	}

	public void setDst_start_date(Date dst_start_date) {
		this.dst_start_date = dst_start_date;
	}

	public Date getDst_end_date() {
		return dst_end_date;
	}

	public void setDst_end_date(Date dst_end_date) {
		this.dst_end_date = dst_end_date;
	}

	public int getDst_variation_minutes() {
		return dst_variation_minutes;
	}

	public void setDst_variation_minutes(int dst_variation_minutes) {
		this.dst_variation_minutes = dst_variation_minutes;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
