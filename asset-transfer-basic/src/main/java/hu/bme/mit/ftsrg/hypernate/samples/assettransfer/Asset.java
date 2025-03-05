/* SPDX-License-Identifier: Apache-2.0 */
package hu.bme.mit.ftsrg.hypernate.samples.assettransfer;

import hu.bme.mit.ftsrg.hypernate.annotations.AttributeInfo;
import hu.bme.mit.ftsrg.hypernate.annotations.PrimaryKey;
import lombok.*;
import lombok.experimental.FieldNameConstants;
import org.hyperledger.fabric.contract.annotation.DataType;
import org.hyperledger.fabric.contract.annotation.Property;

@Builder(toBuilder = true)
@FieldNameConstants
@PrimaryKey(@AttributeInfo(name = Asset.Fields.assetID))
@DataType
public record Asset(
    @Property String assetID,
    @Property String color,
    @Property int size,
    @Property int appraisedValue,
    @Property String owner) {}
