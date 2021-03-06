/*
 * ProActive Parallel Suite(TM):
 * The Open Source library for parallel and distributed
 * Workflows & Scheduling, Orchestration, Cloud Automation
 * and Big Data Analysis on Enterprise Grids & Clouds.
 *
 * Copyright (c) 2007 - 2017 ActiveEon
 * Contact: contact@activeeon.com
 *
 * This library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation: version 3 of
 * the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * If needed, contact us to obtain a release under GPL Version 2 or 3
 * or a different license than the AGPL.
 */
package org.ow2.proactive_grid_cloud_portal.rm.client.monitoring.charts;

import java.util.LinkedHashMap;

import org.ow2.proactive_grid_cloud_portal.common.client.Model;
import org.ow2.proactive_grid_cloud_portal.rm.client.RMController;

import com.google.gwt.visualization.client.AbstractDataTable.ColumnType;
import com.google.gwt.visualization.client.DataTable;
import com.google.gwt.visualization.client.LegendPosition;
import com.google.gwt.visualization.client.visualizations.corechart.AreaChart;
import com.google.gwt.visualization.client.visualizations.corechart.CoreChart;
import com.google.gwt.visualization.client.visualizations.corechart.Options;
import com.smartgwt.client.widgets.form.fields.SelectItem;
import com.smartgwt.client.widgets.form.fields.events.ChangedEvent;
import com.smartgwt.client.widgets.form.fields.events.ChangedHandler;


/**
 * Chart that retrieves information from several MBeans and shows time on Y axis.
 */
public abstract class MBeansTimeAreaChart extends MBeansChart {

    private boolean initializing = true;

    public MBeansTimeAreaChart(RMController controller, String jmxServerUrl, String mbean, String attribute,
            String title) {
        this(controller, jmxServerUrl, mbean, new String[] { attribute }, title);
    }

    public MBeansTimeAreaChart(RMController controller, String jmxServerUrl, String mbean, String[] attributes,
            String title) {
        super(controller, jmxServerUrl, mbean, attributes, title);

        loadOpts.setLegend(LegendPosition.RIGHT);
        loadTable.addColumn(ColumnType.STRING);
        // fake column to draw the chart properly
        // with mbeans we don't know how many columns we will have until receive first results
        loadTable.addColumn(ColumnType.NUMBER);
    }

    public boolean initColumns() {
        if (initializing) {
            // removing fake column
            loadTable.removeColumn(1);
            initializing = false;
            return true;
        }

        return false;
    }

    @Override
    public CoreChart createChart(DataTable data, Options opts) {
        return new AreaChart(data, opts);
    }

}
