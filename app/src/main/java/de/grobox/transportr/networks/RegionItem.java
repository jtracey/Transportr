/*
 *    Transportr
 *
 *    Copyright (c) 2013 - 2017 Torsten Grote
 *
 *    This program is Free Software: you can redistribute it and/or modify
 *    it under the terms of the GNU General Public License as
 *    published by the Free Software Foundation, either version 3 of the
 *    License, or (at your option) any later version.
 *
 *    This program is distributed in the hope that it will be useful,
 *    but WITHOUT ANY WARRANTY; without even the implied warranty of
 *    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *    GNU General Public License for more details.
 *
 *    You should have received a copy of the GNU General Public License
 *    along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package de.grobox.transportr.networks;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.v4.view.ViewCompat;
import android.view.View;

import com.mikepenz.fastadapter.IItem;
import com.mikepenz.fastadapter.expandable.items.AbstractExpandableItem;
import com.mikepenz.fastadapter.listeners.OnClickListener;

import java.util.Comparator;
import java.util.List;

import javax.annotation.ParametersAreNonnullByDefault;

import de.grobox.transportr.R;

@ParametersAreNonnullByDefault
class RegionItem extends AbstractExpandableItem<RegionItem, RegionViewHolder, TransportNetworkItem> {

	private final Region region;

	RegionItem(Region region) {
		super();
		this.region = region;
	}

	@IdRes
	@Override
	public int getType() {
		return R.id.list_item_transport_region;
	}

	@Override
	@LayoutRes
	public int getLayoutRes() {
		return R.layout.list_item_transport_region;
	}

	@Override
	public void bindView(RegionViewHolder ui, List<Object> payloads) {
		super.bindView(ui, payloads);
		ui.bind(region, isExpanded());
	}

	@Override
	public RegionViewHolder getViewHolder(View view) {
		return new RegionViewHolder(view);
	}

	@Override
	public long getIdentifier() {
		return region.getName();
	}

	@Override
	public OnClickListener<RegionItem> getOnItemClickListener() {
		return (v, adapter, item, position) -> {
			if (item.getSubItems() != null) {
				if (!item.isExpanded()) {
					ViewCompat.animate(v.findViewById(R.id.chevron)).rotation(180).start();
				} else {
					ViewCompat.animate(v.findViewById(R.id.chevron)).rotation(0).start();
				}
				return true;
			}
			return false;
		};
	}

	static class RegionComparator implements Comparator<IItem> {

		private final Context context;

		RegionComparator(Context context) {
			super();
			this.context = context;
		}

		@Override
		public int compare(IItem i1, IItem i2) {
			if (i1 instanceof RegionItem && i2 instanceof RegionItem) {
				// sort regions alphabetically
				return ((RegionItem) i1).region.getName(context).compareTo(((RegionItem) i2).region.getName(context));
			}
			return 0;
		}
	}

}
