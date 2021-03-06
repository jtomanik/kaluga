/*

Copyright 2019 Splendo Consulting B.V. The Netherlands

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/

import UIKit
import KotlinNativeFramework

class AlertsViewController: UITableViewController {

    private lazy var alertPresenter = SharedAlertPresenter(builder: AlertsAlertBuilder(viewController: self))

    override func tableView(_ tableView: UITableView, didSelectRowAt indexPath: IndexPath) {
        tableView.deselectRow(at: indexPath, animated: true)
        switch indexPath.row {
        case 0: alertPresenter.showAlert()
        case 1: alertPresenter.showAndDismissAfter(timeSecs: 3)
        case 2: alertPresenter.showList()
        default: ()
        }
    }
}
